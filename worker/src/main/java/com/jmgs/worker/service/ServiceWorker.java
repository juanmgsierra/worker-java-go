package com.jmgs.worker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.jmgs.worker.client.CustomerClientService;
import com.jmgs.worker.client.ProductClientService;
import com.jmgs.worker.dto.Customer;
import com.jmgs.worker.dto.OrderEvent;
import com.jmgs.worker.dto.Products;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ServiceWorker {

    private final ProductClientService productClient;
    private final CustomerClientService customerClient;
    private final OrderService orderService;
    private final RedissonClient redissonClient;
    private final RedisService redisService;

    public void saveOrder(OrderEvent orderEvent, Acknowledgment ack) throws Exception {
        log.info("order id: " + orderEvent.orderId());

        String orderId = orderEvent.orderId();
        RLock lock = redissonClient.getLock("lock:order:" + orderId);

        boolean locked = false;
        try {
            locked = lock.tryLock(0, 60, TimeUnit.SECONDS);

            if (!locked) {
                log.info("Order already processing: " + orderId);
                return;
            }

            Customer customer = customerClient.getCustomerById(orderEvent.customerId()).block();
            if (customer.id() == null || !customer.isActive()) {
                log.error("Customer not found or inactive: " + orderEvent.customerId());
                ack.acknowledge();
                return;
            }

            List<Products> products = new ArrayList<>();
            for (String productId : orderEvent.products()) {
                Products product = productClient.getProductById(productId).block();
                if (product.productId() == null) {
                    log.error("Product not found: " + productId);
                    ack.acknowledge();
                    return;
                }
                products.add(product);
            }

            orderService.saveOrder(orderEvent, products);
            ack.acknowledge();

        } catch (Exception e) {
            int attempts = redisService.incrementOrderRetry(orderId);
            if (attempts >= 3) {
                orderService.saveOrderError(orderEvent, e.getMessage());
                ack.acknowledge();
                return;
            }
            throw e;
        } finally {
            if (locked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
