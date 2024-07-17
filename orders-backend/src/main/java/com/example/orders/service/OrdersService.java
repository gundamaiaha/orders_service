package com.example.orders.service;

import com.example.orders.constants.OrderStatus;
import com.example.orders.dto.OrderSearchCriteria;
import com.example.orders.model.Order;
import com.example.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;


    public List<Order> searchSuccessOrders(OrderSearchCriteria orderSearchCriteria) {
       log.info("in searchSuccessOrders ");
        return searchOrders(orderSearchCriteria, OrderStatus.SUCCESS);
    }

    public List<Order> searchFailedOrders(OrderSearchCriteria orderSearchCriteria) {
        return searchOrders(orderSearchCriteria, OrderStatus.FAILED);
    }


    private List<Order> searchOrders(OrderSearchCriteria orderSearchCriteria, OrderStatus orderStatus) {
        log.info("in searchOrders");
        LocalDateTime startDateTime = Optional.ofNullable(orderSearchCriteria).map(OrderSearchCriteria::getStartDateTime).orElse(null);
        LocalDateTime endDateTime = Optional.ofNullable(orderSearchCriteria).map(OrderSearchCriteria::getEndDateTime).orElse(null);
        LocalDate searchDate = Optional.ofNullable(orderSearchCriteria).map(OrderSearchCriteria::getSearchDate).orElse(null);

        //default search
        if (startDateTime == null && endDateTime == null && searchDate == null) {
            // Default search criteria: today 6:30 PM CST to 3 hours ahead
            LocalDateTime cstDateTime = LocalDateTime.now(ZoneId.of("America/Chicago"));
            startDateTime = cstDateTime.withHour(18).withMinute(30).atZone(ZoneId.of("America/Chicago")).toOffsetDateTime().atZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            endDateTime = startDateTime.plusHours(3);
        }

        //searchDate search
        else if (searchDate != null) {
            startDateTime = searchDate.atStartOfDay();
            endDateTime = searchDate.plusDays(1).atStartOfDay();
        }

        return ordersRepository.findByOrderTimeBetweenAndStatusOrderByOrderTime(startDateTime, endDateTime, orderStatus);
    }

    public List<Order> getAllOrders(){
        log.info("getting all orders");
        return ordersRepository.findAll();
    }


}
