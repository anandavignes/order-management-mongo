package com.ally.order.mgt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ally.order.mgt.domain.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    public Order findByOrdernumber(String ordernumber);

}
