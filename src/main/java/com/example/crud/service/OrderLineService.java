package com.example.crud.service;

import com.example.crud.entity.Order;
import com.example.crud.entity.OrderLine;

import java.util.List;
import java.util.Optional;

public interface OrderLineService {
    Iterable<OrderLine> findAllOrderLine();
    OrderLine findById(Long orderId);
    void save (OrderLine orderLine);
    void remove (OrderLine orderLine);
    List<OrderLine> getListOrderLineInOrder(Long orderId);
}
