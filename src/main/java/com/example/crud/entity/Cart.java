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

//    private List<CartItem> cartItems = new ArrayList<>();
    @ManyToMany(mappedBy = "tblCART")
    private List<Product> productList;

    @Column(name = "total_money")
    private double totalMoney;

    public Cart(){

    }

    public Cart(User user) {
        this.user = user;
//        this.cartItems = cartItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public List<CartItem> getCartItems() {
//        return cartItems;
//    }
//
//    public void setCartItems(List<CartItem> cartItems) {
//        this.cartItems = cartItems;
//    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }
}
