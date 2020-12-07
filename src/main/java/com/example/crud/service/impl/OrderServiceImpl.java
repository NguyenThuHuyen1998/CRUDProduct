package com.example.crud.service.impl;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.Order;
import com.example.crud.entity.OrderLine;
import com.example.crud.form.OrderForm;
import com.example.crud.form.OrderLineForm;
import com.example.crud.repository.OrderLineRepository;
import com.example.crud.repository.OrderRepository;
import com.example.crud.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private OrderLineRepository orderLineRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderLineRepository orderLineRepository){
        this.orderRepository= orderRepository;
        this.orderLineRepository= orderLineRepository;
    }

    @Override
    public List<Order> findAllOrder() {
        return (List<Order>) orderRepository.findAll();
    }


    @Override
    public OrderForm findById(Long orderId) {
        try{
            Order order= orderRepository.findById(orderId).get();
            List<OrderLine> orderLines= orderLineRepository.getListOrderLineInOrder(orderId);
            List<OrderLineForm> orderLineForms= new ArrayList<>();
            for(OrderLine orderLine: orderLines){
                OrderLineForm orderLineForm= new OrderLineForm(orderLine);
                orderLineForms.add(orderLineForm);
            }
            OrderForm orderForm= new OrderForm(order, orderLineForms);
            return orderForm;
        }
        catch (Exception e){
            logger.error(String.valueOf(e));
            return null;
        }
    }

    @Override
    public Order getOrder(Long orderId){
        return orderRepository.findById(orderId).get();
    }

    @Override
    public List<Order> getListOrderByUserId(long userId) {
        return orderRepository.getListOrderByUserId(userId);
    }

    @Override
    public List<Order> getListOrderByStatus(String status) {
        try{
            Sort sort= Sort.by("dateSell").descending();
            List<Order> list= orderRepository.getListByStatus(status, sort);
            return list;
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void remove(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public List<Order> filterOrder(Map<String, Object> filter) {
        String status= (String) filter.get(InputParam.STATUS);
        int limit= (int) filter.get(InputParam.LIMIT);
        List<Order> orderList= findAllOrder();
        if(status!= null){

        }
        return null;
    }

//    @Override
//    public List<OrderLine> getListOrderLine(Long orderId) {
//        return orderRepository.getListOrderLineInOrder(orderId);
//    }


}
