package com.jmgs.worker.service;

import org.springframework.stereotype.Service;

import com.jmgs.worker.client.CustomerClientService;
import com.jmgs.worker.client.ProductClientService;
import com.jmgs.worker.dto.OrderEvent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ServiceWorker {
    
    private final ProductClientService productClient;
    private final CustomerClientService customerClient;


    public void saveOrder(OrderEvent orderEvent){
        log.info("order id: "+ orderEvent.orderId());

        // revisar en redis si existe orderId

        // revisar el cliente exista, y este activo

        // revisar los productos que existan

        // guardar en base de datos
    }


}
