package com.jmgs.worker.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Customer (
    @JsonAlias("_id") String id,
    String name,
    Boolean isActive
) {}
