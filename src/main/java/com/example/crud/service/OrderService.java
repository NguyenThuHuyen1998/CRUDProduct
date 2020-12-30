package com.example.crud.service;

import com.example.crud.entity.Address;
import com.example.crud.entity.Cart;
import com.example.crud.entity.Order;
import com.example.crud.entity.User;
import com.example.crud.response.OrderResponse;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/*
    created by HuyenNgTn on 15/11/2020
*/
public interface OrderService {
    List<Order> findAllOrder();
    Order findById(Long orderId);
    Order getOrder(Long orderId);
    List<Order> getListOrderByUserId(long userId);
    void save (Order order);
    void remove (Order order);
    List<Order> filterOrder(Map<String, Object> filter) throws ParseException;
    OrderResponse createOrder(User user);
}
