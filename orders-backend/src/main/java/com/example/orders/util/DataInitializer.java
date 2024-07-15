package com.example.orders.util;

import com.example.orders.constants.OrderStatus;
import com.example.orders.model.Order;
import com.example.orders.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public void run(String... args) throws Exception {
        String zoneId = ZoneId.SHORT_IDS.get("CST");
        //first criteria
        LocalDateTime cstDateTime = LocalDateTime.now(ZoneId.of("America/Chicago"));
        LocalDateTime utcDateTime = cstDateTime.withHour(18).withMinute(30).
                atZone(ZoneId.of("America/Chicago")).toOffsetDateTime().atZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        insertOrders(utcDateTime, 1);

        //second criteria && third criteria
        insertOrders(LocalDate.now().atStartOfDay(), 2);


//
//        Order order1 = new Order("Alice", OrderStatus.SUCCESS, LocalDateTime.now().minusDays(1));
//        Order order2 = new Order("Bob", OrderStatus.FAILED, LocalDateTime.now().minusDays(2));
//        Order order3 = new Order("Charlie", OrderStatus.SUCCESS, LocalDateTime.now().minusDays(3));
//        Order order4 = new Order("David", OrderStatus.FAILED, LocalDateTime.now().minusDays(4));
//        Order order5 = new Order("Eve", OrderStatus.SUCCESS, LocalDateTime.now().minusDays(5));
//        Order order6 = new Order("Frank", OrderStatus.FAILED, LocalDateTime.now().minusDays(6));

    }


    private void insertOrders(LocalDateTime orderTime, int criteria) {
        Order order1 = new Order("Alice_" + criteria, OrderStatus.SUCCESS, orderTime);
        Order order2 = new Order("Bob_" + criteria, OrderStatus.FAILED, orderTime.plusHours(1));
        Order order3 = new Order("Charlie " + criteria, OrderStatus.SUCCESS, orderTime.plusMinutes(30L));
        Order order4 = new Order("David " + criteria, OrderStatus.FAILED, orderTime.plusHours(2));
        Order order5 = new Order("Eve " + criteria, OrderStatus.SUCCESS, orderTime.plusMinutes(42));
        Order order6 = new Order("Frank " + criteria, OrderStatus.FAILED, orderTime.plusHours(1));
        Order order7 = new Order("James " + criteria, OrderStatus.SUCCESS, orderTime.plusMinutes(45L));
        Order order8 = new Order("Smith " + criteria, OrderStatus.FAILED, orderTime.plusHours(5));
        Order order9 = new Order("Kim " + criteria, OrderStatus.SUCCESS, orderTime.plusHours(5));
        Order order10 = new Order("James " + criteria, OrderStatus.FAILED, orderTime.plusHours(5));
        Order order11 = new Order("Dan " + criteria, OrderStatus.FAILED, LocalDateTime.now());

        List<Order> orders = List.of(order1, order2, order3, order4, order5, order6, order7, order8, order9, order10, order11);
        ordersRepository.saveAll(orders);

    }


}
