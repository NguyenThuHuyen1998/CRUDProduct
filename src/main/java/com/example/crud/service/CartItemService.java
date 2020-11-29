package com.example.crud.service;

import com.example.crud.entity.Cart;
import com.example.crud.entity.CartItem;
import com.example.crud.entity.Product;

import java.util.List;
import java.util.Optional;

/*
    created by HuyenNgTn on 22/11/2020
*/
public interface CartItemService {

     void save(CartItem cartItem);
     CartItem getCartLineById(long cartLineId) ;
     CartItem getCartItemByProductId (long userId, long productId);
     void updateQuantityCartItem(CartItem cartItem);
     void deleteCartItem(long userId, long productId);
     void deleteAllCartItem(long userId);
     List<CartItem> getListCartItemInCart(long userId);
}
