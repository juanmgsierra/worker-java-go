package com.jmgs.worker.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.jmgs.worker.dto.Products;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "orders")
public class Order {
  
    @Id
    private String _id;
    private String orderId;
    private String customerId;
    private List<Products> products;
}
