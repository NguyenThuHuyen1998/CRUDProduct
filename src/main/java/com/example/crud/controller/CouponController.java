package com.example.crud.controller;

import com.example.crud.entity.Coupon;
import com.example.crud.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CouponController {

    private CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService){
        this.couponService= couponService;
    }

    @GetMapping(value = "/adminPage/coupons")
    public ResponseEntity<Coupon> getListCoupon(HttpServletRequest request){
//        try{
//
//        }
//        catch ()
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
