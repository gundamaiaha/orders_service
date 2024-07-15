package com.example.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearchCriteria {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDate searchDate;
}
