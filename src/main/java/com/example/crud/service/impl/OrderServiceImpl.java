package com.example.crud.service.impl;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.*;
import com.example.crud.helper.TimeHelper;
import com.example.crud.output.OrderLineForm;
import com.example.crud.predicate.PredicateOrderFilter;
import com.example.crud.repository.CartItemRepository;
import com.example.crud.repository.CartRepository;
import com.example.crud.repository.OrderLineRepository;
import com.example.crud.repository.OrderRepository;
import com.example.crud.response.OrderResponse;
import com.example.crud.service.OrderService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/*
    created by HuyenNgTn on 15/11/2020
*/
@Service
public class OrderServiceImpl implements OrderService {
    public static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

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
        try{
            Order order= orderRepository.findById(orderId).get();
//            List<OrderLine> orderLines= orderLineRepository.getListOrderLineInOrder(orderId);
//            List<OrderLineForm> orderLineForms= new ArrayList<>();
//            for(OrderLine orderLine: orderLines){
//                OrderLineForm orderLineForm= new OrderLineForm(orderLine);
//                orderLineForms.add(orderLineForm);
//            }
//            OrderForm orderForm= new OrderForm(order, orderLineForms);
            return order;
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

//    @Override
//    public List<Order> getListOrderByStatus(OrderStatus status) {
//        try{
//            //Sort sort= Sort.by("dateSell").descending();
//            List<Order> list= orderRepository.getListByStatus(status);
//            return list;
//        }
//        catch (Exception e){
//            return null;
//        }
//    }

//    @Override
//    public List<Order> getListOrderByTime(String timeStart, String timeEnd) {
//        Date start= new Date(timeStart+" 00:00:00");
//        Date end= new Date((timeEnd+" 23:59:59"));
//        long startTime= start.getTime();
//        long endTime= end.getTime();
//        List<Order> orderList= orderRepository.getListByTime(startTime, endTime);
//        System.out.println(orderList);
//        return orderList;
//    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void remove(Order order) {
        orderRepository.delete(order);
    }

    @Override
    public List<Order> filterOrder(Map<String, Object> filter) throws ParseException {
        long userId= (long) filter.get(InputParam.USER_ID);
        String status= (String) filter.get(InputParam.STATUS);
        String dateStart= (String) filter.get(InputParam.TIME_START);
        String dateEnd= (String) filter.get(InputParam.TIME_END);
        double priceMin= (double) filter.get(InputParam.PRICE_MIN);
        double priceMax= (double) filter.get(InputParam.PRICE_MAX);
        String sortBy= (String) filter.get(InputParam.SORT_BY);

        Predicate<Order> predicate= null;
        PredicateOrderFilter predicateOrderFilter= PredicateOrderFilter.getInstance();
        Predicate<Order> checkStatus= predicateOrderFilter.checkStatus(status);
        Predicate<Order> checkPrice= predicateOrderFilter.checkPrice(priceMin, priceMax);
        Predicate<Order> checkDate= predicateOrderFilter.checkDate(dateStart, dateEnd);
        Predicate<Order> checkUser= predicateOrderFilter.checkUser(userId);
        predicate= checkPrice.and(checkDate).and(checkUser).and(checkStatus);
        List<Order> orderList=predicateOrderFilter.filterOrder(findAllOrder(), predicate);
        //orderList= sortByDateSell(orderList, sortBy);
        return orderList;

    }

    @Override
    public OrderResponse createOrder(User user) {
        long userId = user.getUserId();
        Order order = new Order(user, OrderStatus.PROCESSING, new Date().getTime());
        List<CartItem> cartItemList = cartItemRepository.getListCartItemInCart(userId);
        List<OrderLineForm> orderLineForms= new ArrayList<>();
        if (cartItemList != null && cartItemList.size() > 0) {
            order = new Order(user, OrderStatus.PROCESSING, new Date().getTime());
            for (CartItem cartItem : cartItemList) {
                OrderLine orderLine = new OrderLine(cartItem, order);
                OrderLineForm orderLineForm= new OrderLineForm(orderLine);
                orderLineForms.add(orderLineForm);
            }
        }
        OrderResponse orderResponse= new OrderResponse(order, orderLineForms);
        return orderResponse;
    }


    public List<Order> sortByDateSell(List<Order> orders, String sortBy){
//        if(sortBy.equals(InputParam.INCREASE)){
//            Collections.sort(orders, new Comparator<Order>() {
//                public int compare(Order o1, Order o2) {
//                    return o1.getDateSell() > o2.getDateSell() ? 1 : (o1 == o2 ? 0 : -1);
//                }
//            });
//        }
//        if (sortBy.equals(InputParam.DECREASE)){
//            Collections.sort(orders, new Comparator<Order>() {
//                public int compare(Order o1, Order o2) {
//                    return o1.getDateSell() < o2.getDateSell() ? 1 : (o1 == o2 ? 0 : -1);
//                }
//            });
//        }
        return orders;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
        Date date= simpleDateFormat.parse("22/12/2020");
        System.out.println(date.getTime());
    }
}
