package com.example.crud.service.impl;

import com.example.crud.entity.Order;
import com.example.crud.entity.OrderLine;
import com.example.crud.repository.OrderLineRepository;
import com.example.crud.service.OrderLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
    created by HuyenNgTn on 18/11/2020
*/
@Service
public class OrderLineServiceImpl implements OrderLineService {
    public static final Logger logger = LoggerFactory.getLogger(OrderLineServiceImpl.class);

    private OrderLineRepository orderLineRepository;

    @Autowired
    public OrderLineServiceImpl(OrderLineRepository orderLineRepository){
        this.orderLineRepository= orderLineRepository;
    }

    @Override
    public List<OrderLine> findAllOrderLine() {
        return (List<OrderLine>) orderLineRepository.findAll();

    }

    @Override
    public Optional<OrderLine> findById(Long orderId) {
        return orderLineRepository.findById(orderId);
    }

    @Override
    public void save(OrderLine orderLine) {
        orderLineRepository.save(orderLine);
    }

    @Override
    public void remove(OrderLine orderLine) {
        orderLineRepository.delete(orderLine);
    }

    @Override
    public List<OrderLine> getListOrderLineInOrder(Long orderId) {
        return orderLineRepository.getListOrderLineInOrder(orderId);
    }
}
