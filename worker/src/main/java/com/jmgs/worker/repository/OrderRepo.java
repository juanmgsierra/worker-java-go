package com.jmgs.worker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jmgs.worker.entity.Order;

@Repository
public interface OrderRepo extends MongoRepository<Order, String>{
    
}
