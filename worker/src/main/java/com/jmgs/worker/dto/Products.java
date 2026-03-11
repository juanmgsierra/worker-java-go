package com.jmgs.worker.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Products(
    @JsonAlias("_id") String productId, 
    String name,
    BigDecimal price
) {}
