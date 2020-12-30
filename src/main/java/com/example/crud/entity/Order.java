package com.example.crud.entity;


import javax.persistence.*;
import java.io.Serializable;

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

    @Column(name = "time")
    private long time;

    @Column(name = "total")
    private double total;

    @Column(name = "real_pay")
    private double realPay;

    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address", nullable = false)
    private Address address;


    @Column(name = "voucher_id", nullable = true)
    private Voucher voucher;


    public Order(User user, OrderStatus status, long time) {
        this.user = user;
        this.status = status;
        this.time= time;
    }


    public Order(long orderId, User user, long time, double total, double realPay, OrderStatus status, String note, Address address, Voucher voucher) {
        this.orderId = orderId;
        this.user = user;
        this.time = time;
        this.total = total;
        this.realPay = realPay;
        this.status = status;
        this.note = note;
        this.address = address;
        this.voucher = voucher;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }


}
