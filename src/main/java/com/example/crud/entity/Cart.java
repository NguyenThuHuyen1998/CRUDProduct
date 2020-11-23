package com.example.crud.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
    created by HuyenNgTn on 18/11/2020
*/
@Entity
@Table(name = "tblCART")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id", nullable = false)
    private long cartId;


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    @Column(name = "order_num")
    private int orderNum;

    public Cart(){

    }

    public Cart(User user, List<CartItem> cartItems, int orderNum) {
        this.user = user;
        this.cartItems = cartItems;
        this.orderNum = orderNum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartLines() {
        return cartItems;
    }

    public void setCartLines(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }


}
