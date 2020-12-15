package com.example.crud.service;

import com.example.crud.entity.Order;
import com.example.crud.form.OrderForm;

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
    List<Order> getListOrderByStatus(String status);
    List<Order> getListOrderByTime(String timeStart, String timeEnd);
    void save (Order order);
    void remove (Order order);
    List<Order> filterOrder(Map<String, Object> filter);
}
