package com.example.orders.repository;

import com.example.orders.constants.OrderStatus;
import com.example.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Order,Long> {
    List<Order> findByStatus(String status);

    List<Order> findByOrderTimeBetweenAndStatusOrderByOrderTime(LocalDateTime startTime, LocalDateTime endTime, OrderStatus status);
}
