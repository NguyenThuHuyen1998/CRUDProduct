package com.example.crud.predicate;

import com.example.crud.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
    created by HuyenNgTn on 12/12/2020
*/
public class PredicateOrderFilter {
    public static final Logger logger = LoggerFactory.getLogger(PredicateOrderFilter.class);

    private static final PredicateOrderFilter predicateOrderFilter= new PredicateOrderFilter();

    private PredicateOrderFilter(){

    }

    public static PredicateOrderFilter getInstance(){
        return predicateOrderFilter;
    }

    public Predicate<Order> checkPrice(double minPrice, double maxPrice){
        return (order) ->{
            try{
                if(minPrice== -1){
                    if(maxPrice ==-1){
                        return true;
                    }
                    if(order.getTotalPrice() <= maxPrice){
                        return true;
                    }
                }
                else if(maxPrice== -1){
                    if (order.getTotalPrice() >= minPrice){
                        return true;
                    }
                }
                else if(order.getTotalPrice() >= minPrice && order.getTotalPrice() <= maxPrice){
                    return true;
                }
                return false;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        };

    }


    public Predicate<Order> checkDate(String dateStart, String dateEnd){
        long timeStart= -1;
        long timeEnd= -1;
        if(!dateEnd.equals("-1") && !dateStart.equals("-1")){
            Date start= new Date(dateStart+" 00:00:00");
            timeStart= start.getTime();
            System.out.println(timeStart);
            Date end= new Date((timeEnd+" 23:59:59"));
            timeEnd= end.getTime();
            System.out.println(timeEnd);
        }
        long finalTimeStart = timeStart;
        long finalTimeEnd = timeEnd;
        return (order) ->{
            try{
                if(finalTimeStart == -1){
                    if(finalTimeEnd ==-1){
                        return true;
                    }
                    if(order.getDateSell() <= finalTimeEnd){
                        return true;
                    }
                }
                else if(finalTimeEnd== -1){
                    if (order.getDateSell() >= finalTimeStart){
                        return true;
                    }
                }
                else if(order.getDateSell() >= finalTimeStart && order.getDateSell() <= finalTimeEnd){
                    return true;
                }
                return false;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        };

    }

    public Predicate<Order> checkUser(long userId){
        return order -> {
            if(userId==-1){
                return true;
            }
            if(order.getUser().getUserId()== userId){
                return true;
            }
            return false;
        };
    }

    public Predicate<Order> checkStatus(String status){
        return order -> {
            if(status.equals("")){
                return true;
            }
            if(order.getStatus().equals(status)){
                return true;
            }
            return false;
        };
    }
    public static List<Order> filterOrder (List<Order> orders,
                                           Predicate<Order> predicate)
    {
        return orders.stream()
                .filter( predicate )
                .collect(Collectors.<Order>toList());
    }
}
