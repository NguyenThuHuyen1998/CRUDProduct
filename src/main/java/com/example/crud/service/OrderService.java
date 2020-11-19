package com.example.crud.service;

import com.example.crud.entity.Order;

import java.util.List;

/*
    created by HuyenNgTn on 15/11/2020
*/
public interface OrderService {
    List<Order> findAllOrder();
    Order findById(Long orderId);
//    List<Order> findByProductId(long productId);
    void save (Order order);
    void remove (Order order);
//    List<OrderLine> getListOrderLine(Long orderId);
}
