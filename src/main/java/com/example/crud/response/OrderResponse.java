package com.example.crud.response;

import com.example.crud.entity.Order;
import com.example.crud.entity.OrderStatus;
import com.example.crud.entity.TypeDiscount;
import com.example.crud.helper.TimeHelper;
import com.example.crud.output.OrderLineForm;

import java.io.Serializable;
import java.util.List;

/*
    created by HuyenNgTn on 03/12/2020
*/
public class OrderResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private long orderId;
    private long userId;
    private String time;
    private OrderStatus status;
    private double total;
    private double realPay;
    private double voucher;
    private List<OrderLineForm> orderLineList;

    public OrderResponse(){

    }
    public OrderResponse(Order order, List<OrderLineForm> orderLineForms){
        this.orderId= order.getOrderId();
        this.userId= order.getUser().getUserId();
//        this.time= TimeHelper.getInstance().
        this.status= order.getStatus();
        this.total= order.getTotal();
        if (order.getVoucher().getTypeDiscount()== TypeDiscount.PERCENT){
            double value= order.getVoucher().getValueDiscount();
            this.voucher= value* order.getTotal();
        }
        else {
            this.voucher= order.getVoucher().getValueDiscount();
        }
        this.realPay= order.getTotal()- this.voucher;
        this.orderLineList= orderLineForms;
    }
    public OrderResponse(long orderId, long userId, String time, OrderStatus status, double total, double voucher, double realPay, List<OrderLineForm> orderLineList){
        this.orderId= orderId;
        this.userId= userId;
        this.time= time;
        this.status= status;
        this.voucher= voucher;
        this.total= total;
        this.realPay= realPay;
        this.orderLineList= orderLineList;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getRealPay() {
        return realPay;
    }

    public void setRealPay(double realPay) {
        this.realPay = realPay;
    }

    public double getVoucher() {
        return voucher;
    }

    public void setVoucher(double voucher) {
        this.voucher = voucher;
    }

    public List<OrderLineForm> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<OrderLineForm> orderLineList) {
        this.orderLineList = orderLineList;
    }
}
