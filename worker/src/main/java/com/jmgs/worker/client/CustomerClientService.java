package com.jmgs.worker.client;

import java.time.Duration;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.jmgs.worker.dto.Customer;
import com.jmgs.worker.utils.Utils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerClientService {
    private final WebClient webClient;

    public Mono<Customer> getCustomerById(String id) {
        return webClient.get()
                .uri("/customers/{id}", id)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        response -> Mono.empty())
                .bodyToMono(Customer.class)
                .retryWhen(
                        Retry.backoff(3, Duration.ofMillis(500))
                                .maxBackoff(Duration.ofSeconds(2))
                                .filter(Utils::isRetryableError))
                .timeout(Duration.ofSeconds(15))
                .doOnError(e -> {
                    log.error("Error en call api customers: {}", e.getMessage());
                });
    }
}
