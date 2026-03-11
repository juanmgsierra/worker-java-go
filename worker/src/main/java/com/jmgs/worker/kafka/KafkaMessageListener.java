package com.jmgs.worker.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.jmgs.worker.dto.OrderEvent;
import com.jmgs.worker.service.ServiceWorker;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class KafkaMessageListener {

    private final ServiceWorker serviceWorker;

    @KafkaListener(topics = "order")
    public void listen(OrderEvent order, Acknowledgment ack) throws Exception {
        serviceWorker.saveOrder(order, ack);
    }
}
