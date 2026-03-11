package com.jmgs.worker.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.jmgs.worker.dto.OrderEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "orderErrors")
public class OrderErrors {
    
    @Id
    private String _id;
    private OrderEvent orderEvent;
    private String errorMessage;
}
