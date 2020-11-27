package com.example.crud.controller;

import com.example.crud.entity.*;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

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

    //tạo 1 giỏ hàng sau khi tạo 1 user mới
//    @PostMapping(value = "/carts/{id}")
//    public ResponseEntity<Cart> createCart(@PathVariable("id") long userId){
//        Optional<User> user= userService.findById(userId);
//        if(user.isEmpty()){
//            return new ResponseEntity("User is not exist", HttpStatus.BAD_REQUEST);
//        }
//        long currentCartId= cartService.getCartIdByUserId(userId);
//        if(currentCartId==0){
//            Cart cart= new Cart(user.get(), null, 0);
//            cartService.save(cart);
//            return new ResponseEntity("Success", HttpStatus.OK);
//        }
//        return new ResponseEntity("Cart of this user is exist", HttpStatus.OK);
//    }

    @GetMapping(value = "/carts/products/{id}")
    public ResponseEntity<Cart> getListProduct(@PathVariable("id") long cartId){
        Optional<Cart> cart= cartService.getCartById(cartId);
        if(cart.isEmpty()){
            return new ResponseEntity("User is not exist", HttpStatus.BAD_REQUEST);
        }
        if(cartId!= 0){
            List<CartItem> cartItems= cartService.getlistCartItem(cartId);
            return new ResponseEntity(cartItems, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/carts")
    public ResponseEntity<Cart> getListCart(){
        List<Cart> carts= cartService.getlistCart();
        return new ResponseEntity(carts, HttpStatus.OK);
    }

}
