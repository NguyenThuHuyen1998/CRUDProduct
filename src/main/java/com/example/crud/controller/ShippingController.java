package com.example.crud.controller;

import com.example.crud.entity.Address;
import com.example.crud.service.JwtService;
import com.example.crud.service.ShipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ShippingController {

    public static final Logger logger = LoggerFactory.getLogger(ShippingController.class);

    private ShipService shipService;
    private JwtService jwtService;

    @Autowired
    public ShippingController(ShipService shipService, JwtService jwtService){
        this.shipService= shipService;
        this.jwtService= jwtService;
    }

    @PostMapping(value = "/userPage/addressShip")
    public ResponseEntity<Address> createAddressShip(@RequestBody Address address,
                                                     HttpServletRequest request){
        if(jwtService.isCustomer(request)){

        }
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
