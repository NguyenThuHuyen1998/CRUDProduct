package com.example.crud.controller;

import com.example.crud.entity.Order;
import com.example.crud.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
    created by HuyenNgTn on 15/11/2020
*/
@RestController
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getAllOrder() {
        List<Order> orderList = orderService.findAllOrder();
        return new ResponseEntity(orderList, HttpStatus.OK);
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        orderService.save(order);
        return new ResponseEntity(order, HttpStatus.CREATED);
    }

}
