package com.example.crud.controller;

import com.example.crud.entity.*;
import com.example.crud.form.CartForm;
import com.example.crud.service.CartItemService;
import com.example.crud.service.CartService;
import com.example.crud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
    created by HuyenNgTn on 23/11/2020
*/
@RestController
public class CartController {
    public static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private CartService cartService;

    private UserService userService;

    private CartItemService cartItemService;

    @Autowired
    public CartController(CartService cartService, UserService userService, CartItemService cartItemService){
        this.cartService= cartService;
        this.userService= userService;
        this.cartItemService= cartItemService;
    }

    @GetMapping(value = "/cart/{id}")
    public ResponseEntity<Cart> getACart(@PathVariable("id") long userId){
        try{
            Cart cart= cartService.getCartByUserId(userId);
            List<CartItem> cartItemList= cartItemService.getListCartItemInCart(userId);
            if(cartItemList.size()>0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            CartForm cartForm= new CartForm(cart, cartItemList);
            return new ResponseEntity(cartForm, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
