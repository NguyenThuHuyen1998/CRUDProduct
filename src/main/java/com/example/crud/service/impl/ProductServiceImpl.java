package com.example.crud.service.impl;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.Product;
import com.example.crud.repository.ProductRepository;
import com.example.crud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository= productRepository;
    }


    @Override
    public List<Product> findAllProduct() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return productRepository.findById(productId);
    }
//
    @Override
    public List<Product> findByCategoryID(Long categoryId) {
        return productRepository.findProductByCategoryId(categoryId);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void remove(Product product) {
        productRepository.delete(product);
    }

    public List<Product> filterProduct(Map<String, Object> filter){
        double priceMin= (double) filter.get(InputParam.PRICE_MIN);
        double priceMax= (double) filter.get(InputParam.PRICE_MAX);
        long categoryId= (long) filter.get(InputParam.CATEGORY_ID);
        List<Product> productInput= findAllProduct();
        List<Product> productFilter= new ArrayList<>();
        if(categoryId!= 0){
            productFilter= productRepository.findProductByCategoryId(categoryId);
            productInput= ListUtils.intersection(productInput, productFilter);
        }

        if(priceMax!= 0 && priceMin !=0 && priceMin< priceMax){
            for(Product product: productFilter){
                if(priceMin <= product.getPrice() && product.getPrice()<= priceMax){
                    productFilter.add(product);
                }
            }
            productInput= productFilter;
        }

        return productInput;
    }


}
