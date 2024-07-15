package com.example.orders.model;

import com.example.orders.constants.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String customerName;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime orderTime;


    public Order() {
    }

    public Order(String customerName, OrderStatus status, LocalDateTime orderTime) {
        this.customerName = customerName;
        this.status = status;
        this.orderTime = orderTime;
    }
}
