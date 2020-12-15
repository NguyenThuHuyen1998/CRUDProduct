package com.example.crud.response;

import com.example.crud.entity.Order;
import com.example.crud.entity.OrderLine;
import com.example.crud.entity.User;
import com.example.crud.form.OrderLineForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/*
    created by HuyenNgTn on 03/12/2020
*/
public class OrderResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private long orderId;
    private long userId;
    private long dateSell;
    private String status;
    private double totalPrice;
    private List<OrderLineForm> orderLineList;

    public OrderResponse(){

    }
    public OrderResponse(Order order, List<OrderLineForm> orderLineForms){
        this.orderId= order.getOrderId();
        this.userId= order.getUser().getUserId();
        this.dateSell= order.getDateSell();
        this.status= order.getStatus();
        this.totalPrice= order.getTotalPrice();
        this.orderLineList= orderLineForms;
    }
    public OrderResponse(long orderId, long userId, long dateSell, String status, double totalPrice, List<OrderLineForm> orderLineList){
        this.orderId= orderId;
        this.userId= userId;
        this.dateSell= dateSell;
        this.status= status;
        this.totalPrice= totalPrice;
        this.orderLineList= orderLineList;
    }

//    public OrderForm(Order order){
//        this.orderId= order.getOrderId();
//        this.userId= order.getUser().getUserId();
//        this.dateSell= order.getDateSell();
//        this.status= order.getStatus();
//        this.totalPrice= order.getTotalPrice();
//        this.orderLineList = order.getOrderLine() == null ? null :
//                commentBO.getCommentBOs().stream().map(cmt -> {
//                    CommentDTO commentDTO = new CommentDTO();
//                    commentDTO.setId(cmt.getId());
//                    commentDTO.setContent(cmt.getContent());
//                    commentDTO.setCreatedAt(cmt.getCreatedAt());
//                    commentDTO.setParentId(cmt.getCommentBO() == null ? null : cmt.getCommentBO().getId());
//                    commentDTO.setCreatedById(cmt.getCreatedBy() == null ? null : cmt.getCreatedBy().getId());
//                    commentDTO.setCreatedBy(cmt.getCreatedBy() == null ? null :cmt.getCreatedBy().getName());
//                    return commentDTO;
//                }).collect(Collectors.toList());
//    }

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

    public long getDateSell() {
        return dateSell;
    }

    public void setDateSell(long dateSell) {
        this.dateSell = dateSell;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderLineForm> getOrderLineList() {
        return orderLineList;
    }

    public void setOrderLineList(List<OrderLineForm> orderLineList) {
        this.orderLineList = orderLineList;
    }
}
