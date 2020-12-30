package com.example.crud.controller;

import com.example.crud.entity.Voucher;
import com.example.crud.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class VoucherController {

    private VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService){
        this.voucherService= voucherService;
    }

    @GetMapping(value = "/adminPage/coupons")
    public ResponseEntity<Voucher> getListCoupon(HttpServletRequest request){
//        try{
//
//        }
//        catch ()
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
