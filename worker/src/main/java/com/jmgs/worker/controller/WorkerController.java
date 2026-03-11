package com.jmgs.worker.controller;

import org.springframework.web.bind.annotation.RestController;

import com.jmgs.worker.client.CustomerClientService;

import com.jmgs.worker.client.ProductClientService;

import com.jmgs.worker.dto.Customer;

import com.jmgs.worker.dto.Products;

import lombok.AllArgsConstructor;

import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController

@RequestMapping("worker")

@AllArgsConstructor

public class WorkerController {

    private final ProductClientService productClient;

    private final CustomerClientService customerClientService;

    @GetMapping("/product/{id}")

    public Mono<Products> getProduct(@PathVariable String id) {

        return productClient.getProductById(id);

    }

    @GetMapping("/customer/{id}")

    public Mono<Customer> getCustomer(@PathVariable String id) {

        return customerClientService.getCustomerById(id);

    }

}
