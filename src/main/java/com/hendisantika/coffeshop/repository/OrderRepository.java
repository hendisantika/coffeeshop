package com.hendisantika.coffeshop.repository;

import com.hendisantika.coffeshop.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerEmail(String customerEmail);

    List<Order> findByStatus(Order.OrderStatus status);

    List<Order> findByStoreId(String storeId);

    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);

    List<Order> findAllByOrderByOrderDateDesc();
}
