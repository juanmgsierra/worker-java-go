package com.jmgs.worker.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jmgs.worker.dto.Products;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class ProductClientService {

    private final WebClient webClient;

    public Mono<Products> getProductById(String id) {
        return webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Products.class)
                .onErrorResume(e -> {
                    log.error("Error en call api products", e);
                    return Mono.empty();
                });
    }
}
