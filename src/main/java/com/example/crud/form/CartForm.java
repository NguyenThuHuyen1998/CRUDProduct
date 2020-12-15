package com.example.crud.form;

import com.example.crud.entity.Cart;
import com.example.crud.entity.CartItem;

import java.io.Serializable;
import java.util.List;

public class CartForm implements Serializable {
    private long cartId;
    private List<CartItemForm> cartItemForms;
    private double totalMoney;
    private long userId;

    public CartForm(Cart cart, List<CartItemForm> cartItemForms){
        this.cartId= cart.getCartId();
        this.cartItemForms= cartItemForms;
        this.totalMoney= cart.getTotalMoney();
        this.userId= cart.getUser().getUserId();
    }
    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public List<CartItemForm> getCartItemForms() {
        return cartItemForms;
    }

    public void setCartItems(List<CartItemForm> cartItemForms) {
        this.cartItemForms = cartItemForms;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
