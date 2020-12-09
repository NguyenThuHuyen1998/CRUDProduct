package com.example.crud.controller;

import com.example.crud.entity.*;
import com.example.crud.service.OrderService;
import com.example.crud.service.ProductService;
import com.example.crud.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FeedbackController {

    private UserService userService;
    private OrderService orderService;
    private ProductService productService;

    public FeedbackController(UserService userService, OrderService orderService, ProductService productService){
        this.userService= userService;
        this.productService= productService;
        this.orderService= orderService;
    }

    @PostMapping(value = "/userPage/feedback")
    public ResponseEntity<FeedBack> createFeedback(@RequestBody FeedBack feedBack){
        User user= feedBack.getUser();
        // đoạn này để check user có quyền đăng feedback hay không
        Product product= feedBack.getProduct();
        Order order= feedBack.getOrder();
        //List<CartItem> cartItemList=
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/userPage/feedback")
    public ResponseEntity<FeedBack> deleteFeedback(@RequestBody FeedBack feedBack){
        User user= feedBack.getUser();
        // đoạn này để check user có quyền đăng feedback hay không
        Product product= feedBack.getProduct();
        Order order= feedBack.getOrder();
        //List<CartItem> cartItemList=
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //____________________________________ADMIN______________________________________
    @GetMapping(value = "/adminPage/feedback")
    public ResponseEntity<FeedBack> getListFeedback(@RequestBody FeedBack feedBack){
        User user= feedBack.getUser();
        // đoạn này để check user có quyền đăng feedback hay không
        Product product= feedBack.getProduct();
        Order order= feedBack.getOrder();
        //List<CartItem> cartItemList=
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/adminPage/feedback")
    public ResponseEntity<FeedBack> deleteFeedbackByAdmin(@RequestBody FeedBack feedBack){
        User user= feedBack.getUser();
        // đoạn này để check user có quyền đăng feedback hay không
        Product product= feedBack.getProduct();
        Order order= feedBack.getOrder();
        //List<CartItem> cartItemList=
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
