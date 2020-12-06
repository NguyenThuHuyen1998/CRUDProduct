package com.example.crud.service;

import com.example.crud.entity.Order;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
    created by HuyenNgTn on 15/11/2020
*/
public interface OrderService {
    List<Order> findAllOrder();
    Order findById(Long orderId);
    List<Order> getListOrderByUserId(long userId);
    List<Order> getListOrderByStatus(String status);
    void save (Order order);
    void remove (Order order);
    List<Order> filterOrder(Map<String, Object> filter);
}
