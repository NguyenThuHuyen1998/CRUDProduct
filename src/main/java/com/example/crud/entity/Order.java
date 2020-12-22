package com.example.crud.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
    private User user;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
//    private List<OrderLine> orderLines;


    @Column(name = "date_sell")
    private long dateSell;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "status")
    private String status;

    public Order() {
    }

    public Order(User user, double totalPrice, String status, long dateSell) {
        this.user = user;
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
//
//    public List<OrderLine> getOrderLines() {
//        return orderLines;
//    }
//
//    public void setOrderLines(List<OrderLine> orderLines) {
//        this.orderLines = orderLines;
//    }
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
