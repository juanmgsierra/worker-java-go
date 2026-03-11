package com.jmgs.worker.dto;

import java.util.List;

public record OrderEvent(
    String orderId,
    String customerId,
    List<String> products
) {}