package com.jmgs.worker.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jmgs.worker.dto.Customer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerClientService {
    private final WebClient webClient;

    public Mono<Customer> getCustomerById(String id) {
        return webClient.get()
                .uri("/customers/{id}", id)
                .retrieve()
                .bodyToMono(Customer.class)
                .onErrorResume(e -> {
                    log.error("Error en call api customers", e);
                    return Mono.empty();
                });

    }

}
