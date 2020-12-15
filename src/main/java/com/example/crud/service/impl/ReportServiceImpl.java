package com.example.crud.service.impl;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.Order;
import com.example.crud.entity.OrderLine;
import com.example.crud.form.ReportProduct;
import com.example.crud.predicate.PredicateOrderFilter;
import com.example.crud.repository.OrderLineRepository;
import com.example.crud.repository.OrderRepository;
import com.example.crud.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class ReportServiceImpl implements ReportService {
    private OrderRepository orderRepository;
    private OrderLineRepository orderLineRepository;
    private Map<String, Object> reportOrder;
    private Map<Long, Object> reportProduct;

    @Autowired
    public ReportServiceImpl(OrderRepository orderRepository, OrderLineRepository orderLineRepository){
        this.orderRepository= orderRepository;
        this.orderLineRepository= orderLineRepository;
    }
    @Override
    public List<Order> filterOrder(Map<String, Object> filter) {
        String status= (String) filter.get(InputParam.STATUS);
        String dateStart= (String) filter.get(InputParam.TIME_START);
        String dateEnd= (String) filter.get(InputParam.TIME_END);

        Predicate<Order> predicate= null;
        PredicateOrderFilter predicateOrderFilter= PredicateOrderFilter.getInstance();
        Predicate<Order> checkStatus= predicateOrderFilter.checkStatus(status);
        Predicate<Order> checkDate= predicateOrderFilter.checkDate(dateStart, dateEnd);
        predicate= checkDate.and(checkStatus);
        List<Order> totalOrder= (List<Order>) orderRepository.findAll();
        List<Order> orderList=predicateOrderFilter.filterOrder(totalOrder, predicate);
        return orderList;
    }

    public Map<Long, Object> getReportProduct(List<Order> orders){
        try{
            if(orders.size()>0){
                for(Order order: orders){
                    List<OrderLine> orderLines= orderLineRepository.getListOrderLineInOrder(order.getOrderId());
                    for (OrderLine orderLine: orderLines){
                        long productId= orderLine.getProduct().getId();
                        if(reportProduct.containsKey(productId)){
                            ReportProduct report= (ReportProduct) reportProduct.get(productId);
                            report.setQuantity(report.getQuantity()+ orderLine.getAmount());
                            report.setRevenue(report.getRevenue()+ orderLine.getValueLine());
                        }
                        else {
                            ReportProduct report= new ReportProduct(orderLine.getProduct().getName(), orderLine.getAmount(), orderLine.getValueLine());
                            reportProduct.put(productId, report);
                        }
                    }
                }
                return reportProduct;
            }
            return null;
        }
        catch (Exception e){
            return null;
        }
    }


//    public Map<String, Object> getReportOrder(List<Order> orders){
////        reportOrder.put(InputParam.PROCESSING, 0);
////        reportOrder.put(InputParam.SHIPPING, 0);
////        reportOrder.put(InputParam.FINISHED, 0);
//        if(orders.size()>0){
//            for (Order order: orders){
//                String status= order.getStatus();
//                if(reportOrder.containsKey(status)){
//                    ReportProduct report= (ReportProduct) reportProduct.get(productId);
//                    report.setQuantity(report.getQuantity()+ orderLine.getAmount());
//                    report.setRevenue(report.getRevenue()+ orderLine.getValueLine());
//                }
//            }
//        }
//    }


}
