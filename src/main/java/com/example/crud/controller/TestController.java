package com.example.crud.controller;

import com.example.crud.entity.Product;
import com.example.crud.response.ProductResponse;
import com.example.crud.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private ProductService productService;

    @PostMapping("/test")
    public ResponseEntity<Product> createProduct(@RequestBody ProductResponse productResponse){
        return null;
    }
}
