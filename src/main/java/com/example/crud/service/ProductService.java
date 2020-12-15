package com.example.crud.service;

import com.example.crud.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProduct();
    Product findById(Long productId);
    List<Product> findByCategoryID(Long categoryId);
//    List<Product> findProductByPrice(double priceMin, double priceMax);
    List<Product> filterProduct(Map<String, Object> input);
    List<Product> filterByKeyword(String keyword);
    void save (Product product);
    void remove (Product product);
    Product update(Product product);
    List<Product> filterByDateAdd(int limit, String sortBy);
}
