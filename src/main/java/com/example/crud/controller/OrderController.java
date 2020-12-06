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
    //user chỉ được lấy ds đơn hàng của mình nên bắt buộc có user-id
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
        Order order= null;
        try{
            List<CartItem> cartItemList= cartItemService.getListCartItemInCart(userId);
            User user= userService.findById(userId);
            Cart cart= cartService.getCartByUserId(userId);
            if(cartItemList!= null && cartItemList.size()>0){
                order= new Order(user, cart.getTotalMoney(), InputParam.PROCESSING, new Date().getTime());
                List<OrderLine> orderLines= order.getOrderLines();
                for (CartItem cartItem: cartItemList){
                    OrderLine orderLine= new OrderLine(cartItem, order);
                    orderLines.add(orderLine);
                    orderLineService.save(orderLine);
                    cartItemService.deleteCartItem(cartItem);
                }
                orderService.save(order);
            }
            if(order== null){
                return new ResponseEntity("Add product int cart!!", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/orders")
    public ResponseEntity<Order> getlistOrder(@RequestParam(name = "user-id", required = false, defaultValue = "0") long userId,
                                              @RequestParam(name = "status", required = false, defaultValue = InputParam.PROCESSING) String status,
                                              @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                              @RequestParam(name = "page", required = false, defaultValue = "1") int page){
            List<Order> orderFilter= orderService.getListOrderByStatus(status);
            if(orderFilter!= null && orderFilter.size()>0){
                return new ResponseEntity(orderFilter, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping(value = "/orders/{order-id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("order-id") long orderId,
                                             @RequestParam(required = false, defaultValue = "0", name = "user-id") long userId){
        try{
            Order order= orderService.findById(orderId);
            if(order.getUser().getUserId()!= userId){
                logger.error("User not permitt");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if(!order.getStatus().equals("processing")){
                logger.error("Can't delete this order");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else {
                orderService.remove(order);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        catch (Exception e){
            logger.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //xem chi tiết 1 đơn hàng
    @GetMapping(value = "/order/{order-id}")
    public ResponseEntity<Order> getOrder(@PathVariable("order-id") long orderId,
                                          @RequestParam("user-id") long userId){
        try{
            Order order= orderService.findById(orderId);
            if(order.getUser().getUserId()!= userId){
                logger.error("User not permitt");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
