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
    public Order findById(Long orderId) {
//        return orderRepository.findById(orderId);
        return null;
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

    public List<Order> filterOrder(Map<String, Object> filter){
        long timeStart= (long) filter.get(InputParam.TIME_START);
        long timeEnd= (long) filter.get(InputParam.TIME_END);
        String orderStatus= (String) filter.get(InputParam.STATUS);
        List<Order> orderInput= (List<Order>) orderRepository.findAll();
        List<Order> orderFilter= new ArrayList<>();
        if(timeStart!= 0 && timeEnd!= 0 && timeStart< timeEnd) {
            for(Order order: orderInput) {
                if(order.getDateSell()>= timeStart && order.getDateSell()<= timeEnd){
                    orderFilter.add(order);
                }
            }
            orderInput= orderFilter;
        }
        if(orderStatus!= ""){
            for(Order order: orderFilter){
                if(order.getStatus().equals(orderStatus)){
                    orderFilter.add(order);
                }
            }
            orderInput= orderFilter;
        }

        return orderInput;
    }

}
