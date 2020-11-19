package com.example.crud.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/*
    created by HuyenNgTn on 15/11/2020
*/
@Entity
@Table(name = "tblORDER")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderLine> orderLine;

    @Column(name = "date_sell")
    private long dateSell;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "status")
    private String status;

    public Order() {
    }

    public Order(long orderId, User user, double totalPrice, String status, long dateSell) {
        this.orderId = orderId;
        this.user = user;
//        this.orderLine = orderLine;
        this.dateSell= dateSell;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderLine> getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(List<OrderLine> orderLine) {
        this.orderLine = orderLine;
    }
//
//    public void addOrderLine(OrderLine orderLine){
//        this.orderLine.add(orderLine);
//    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public int getNumberOrderLine(){
//        return orderLine.size();
//    }

    public long getDateSell() {
        return dateSell;
    }

    public void setDateSell(long dateSell) {
        this.dateSell = dateSell;
    }
}
