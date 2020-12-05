package com.example.crud.controller;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.*;
import com.example.crud.form.OrderForm;
import com.example.crud.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/*
    created by HuyenNgTn on 15/11/2020
*/
@RestController
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;
    private CartService cartService;
    private CartItemService cartItemService;
    private UserService userService;
    private OrderLineService orderLineService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService, CartItemService cartItemService, UserService userService, OrderLineService orderLineService) {
        this.orderService = orderService;
        this.cartService= cartService;
        this.cartItemService= cartItemService;
        this.userService= userService;
        this.orderLineService= orderLineService;
    }

    //lấy danh sách đơn hàng
    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getAllOrder(@RequestParam(required = false, defaultValue = "") String status) {
        List<Order> orderList;
        if(!status.equals("")){
            orderList = orderService.getListOrderByStatus(status);
        }
        orderList= orderService.findAllOrder();
        if(orderList!= null && orderList.size()>0){
            return new ResponseEntity(orderList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //tạo mới đơn hàng
    @PostMapping(value = "/orders")
    public ResponseEntity<Order> createOrder(@RequestParam(name = "user-id", required = false) long userId) {
        List<CartItem> cartItemList= cartItemService.getListCartItemInCart(userId);
        User user= userService.findById(userId);
        Cart cart= cartService.getCartByUserId(userId);
        if(cartItemList!= null && cartItemList.size()>0){
            Order order= new Order(user, cart.getTotalMoney() , InputParam.PROCESSING, new Date().getTime());
            orderService.save(order);
            for (CartItem cartItem: cartItemList){
                OrderLine orderLine= new OrderLine(cartItem, order);
                orderLineService.save(orderLine);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //xem chi tiết 1 đơn hàng
//    @GetMapping(value = "/order/{order-id}")
//    public ResponseEntity<OrderForm> getOrder(@PathVariable("order-id") long orderId){
//        Order order= orderService.findById(orderId);
//        List<OrderLine> orderLines= new ArrayList<>();
//        OrderForm orderForm;
//        if(order!= null){
//             orderLines= orderLineService.getListOrderLineInOrder(orderId);
//             orderForm= new OrderForm(orderId, order.getUser().getUserId(), order.getDateSell(), order.getStatus(), order.getTotalPrice(), orderLineService.getListOrderLineForm(orderLines));
//            return new ResponseEntity(orderForm, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

//    @GetMapping(value = "/order/{order-id}")
//    public ResponseEntity<OrderForm> getOrder(@PathVariable("order-id") long orderId){
//        Order order= orderService.findById(orderId).map(OrderForm::new).get();
//        return new ResponseEntity(order, HttpStatus.OK);
//    }

}
