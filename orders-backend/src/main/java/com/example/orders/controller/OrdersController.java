package com.example.orders.controller;

import com.example.orders.dto.OrderSearchCriteria;
import com.example.orders.model.Order;
import com.example.orders.repository.OrdersRepository;
import com.example.orders.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService ordersService;


    @PostMapping("/success")
    public List<Order> getSuccessOrders(@RequestBody(required = false) OrderSearchCriteria orderSearchCriteria) {
        //MDC.put("orderSearchCriteria", orderSearchCriteria.toString());
        return ordersService.searchSuccessOrders(orderSearchCriteria);
    }
    @PostMapping("/failed")
    public List<Order> getFailedOrders(@RequestBody(required = false) OrderSearchCriteria orderSearchCriteria) {
        return ordersService.searchFailedOrders(orderSearchCriteria);
    }

    @GetMapping("/all")
    public List<Order> getAll(){
        MDC.put("allOrderSearch", "true");
        return ordersService.getAllOrders();
    }


//    @GetMapping("/success")
//    public List<Order> getSuccessOrders() {
//        return ordersRepository.findByStatus("SUCCESS");
//    }
//
//    @GetMapping("/failed")
//    public List<Order> getFailedOrders() {
//        return ordersRepository.findByStatus("FAILED");
//    }

}
