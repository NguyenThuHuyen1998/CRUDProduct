package com.example.crud.service;

import com.example.crud.entity.Order;

import java.util.List;
import java.util.Map;

public interface ReportService {
    List<Order> filterOrder(Map<String, Object> filter);

}
