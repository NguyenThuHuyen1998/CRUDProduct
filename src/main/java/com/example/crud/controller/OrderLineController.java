package com.example.crud.controller;

import com.example.crud.entity.Order;
import com.example.crud.entity.OrderLine;
import com.example.crud.service.OrderLineService;
import com.example.crud.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
    created by HuyenNgTn on 18/11/2020
*/
@RestController
public class OrderLineController {
    public static final Logger logger = LoggerFactory.getLogger(OrderLineController.class);

    public OrderLineService orderLineService;

    public OrderService orderService;

    @Autowired
    public OrderLineController(OrderLineService orderLineService, OrderService orderService){
        this.orderLineService= orderLineService;
        this.orderService= orderService;
    }

    @GetMapping(value = "/orderLines", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderLine> getAllOrderLine(){
        List<OrderLine> orderList= (List<OrderLine>) orderLineService.findAllOrderLine();
        return new ResponseEntity(orderList, HttpStatus.OK);
    }

    @PostMapping(value = "/orderLines")
    public ResponseEntity<OrderLine> createOrder(@RequestBody OrderLine orderLine){
        long orderId= orderLine.getOrder().getOrderId();
        Order order= orderService.findById(orderId);
        if(order!= null){
            orderLineService.save(orderLine);
            order.getOrderLine().add(orderLine);
            return new ResponseEntity(orderLine, HttpStatus.CREATED);
        }
        return new ResponseEntity("Validator invalid", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "orderLines/{order-id}")
    public ResponseEntity<OrderLine> getListOrderLine(@PathVariable("order-id") Long orderId){
        List<OrderLine> orderLines= orderLineService.getListOrderLineInOrder(orderId);
        if(orderLines!= null && orderLines.size()>0){
            return new ResponseEntity(orderLines, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
