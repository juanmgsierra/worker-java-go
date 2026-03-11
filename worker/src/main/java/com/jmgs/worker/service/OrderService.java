package com.jmgs.worker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jmgs.worker.dto.OrderEvent;
import com.jmgs.worker.dto.Products;
import com.jmgs.worker.entity.Order;
import com.jmgs.worker.entity.OrderErrors;
import com.jmgs.worker.repository.OrderErrorsRepo;
import com.jmgs.worker.repository.OrderRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderErrorsRepo orderErrorsRepo;

    void saveOrder(OrderEvent orderEvent, List<Products> products) {
        Order order = new Order();
        order.setOrderId(orderEvent.orderId());
        order.setCustomerId(orderEvent.customerId());
        order.setProducts(products);
        orderRepo.save(order);
    }

    void saveOrderError(OrderEvent orderEvent, String errorMessage) {
        OrderErrors orderError = new OrderErrors();
        orderError.setOrderEvent(orderEvent);
        orderError.setErrorMessage(errorMessage);
        orderErrorsRepo.save(orderError);
    }
}
