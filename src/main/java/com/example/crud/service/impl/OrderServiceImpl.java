package com.example.crud.service.impl;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.Order;
import com.example.crud.repository.OrderRepository;
import com.example.crud.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
    created by HuyenNgTn on 15/11/2020
*/
@Service
public class OrderServiceImpl implements OrderService {
    public static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository= orderRepository;
    }

    @Override
    public List<Order> findAllOrder() {
        return (List<Order>) orderRepository.findAll();
    }


    @Override
    public Optional<Order> findById(Long orderId) {
        //Optional<Order> optionalOrder= orderRepository.findById(orderId);
//        return optionalOrder.get();
        return orderRepository.findById(orderId);
    }

    @Override
    public List<Order> getListOrderByUserId(long userId) {
        return orderRepository.getListOrderByUserId(userId);
    }


//    @Override
//    public List<Order> findByOrderStatus(OrderStatus status) {
//        return
//    }

//    @Override
//    public List<Order> findByProductId(long productId) {
//        return orderRepository.getListOrderByProductId(productId);
//    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void remove(Order order) {
        orderRepository.delete(order);
    }

//    @Override
//    public List<OrderLine> getListOrderLine(Long orderId) {
//        return orderRepository.getListOrderLineInOrder(orderId);
//    }

    public List<Order> getListOrderByStatus(String status){
        List<Order> orderInput= (List<Order>) orderRepository.findAll();
        if(status!= ""){
            orderRepository.getListByStatus(status);
        }
        return orderInput;
    }

}
