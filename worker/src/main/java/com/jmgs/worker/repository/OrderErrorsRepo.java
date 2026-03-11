package com.jmgs.worker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jmgs.worker.entity.OrderErrors;

@Repository
public interface OrderErrorsRepo extends MongoRepository<OrderErrors, String> {
    
}
