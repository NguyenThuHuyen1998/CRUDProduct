package com.example.crud.service;

import com.example.crud.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProduct();
    Optional<Product> findById(Long productId);
    List<Product> findByCategoryID(Long categoryId);
    void save (Product product);
    void remove (Product product);
}
