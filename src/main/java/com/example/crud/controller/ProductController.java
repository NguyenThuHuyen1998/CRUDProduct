package com.example.crud.controller;

import com.example.crud.entity.Product;
import com.example.crud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService= productService;
    }

    @GetMapping(value = "/products")
    public ResponseEntity<Product> findAllProduct(){
        List<Product> productList= productService.findAllProduct();
        if(productList== null || productList.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(productList, HttpStatus.OK);
    }

    @PostMapping(value = "/products")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product, UriComponentsBuilder builder){
        productService.save(product);
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setLocation(builder.path("/products/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping(value = "products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable ("id") long productId, @RequestBody Product product){
        Optional<Product> currentProduct= productService.findById(productId);
        if(product== null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            currentProduct.get().setName(product.getName());
            currentProduct.get().setPrice(product.getPrice());
            currentProduct.get().setDescription(product.getDescription());
            productService.save(currentProduct.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping(value = "products/{id}")
    public ResponseEntity<Product> getAProduct(@PathVariable ("id") long productId){
        Optional<Product> currentProduct= productService.findById(productId);
        if(currentProduct== null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(currentProduct, HttpStatus.OK);

    }

    @DeleteMapping(value = "products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable ("id") long productId){
        Optional<Product> product= productService.findById(productId);
        if(product== null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            productService.remove(product.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping(value = "products/cate/{id}")
    public ResponseEntity<Product> findProductByCategory(@PathVariable ("id") long categoryId){
        List<Product> products= productService.findByCategoryID(categoryId);
        if(products== null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(products, HttpStatus.OK);

    }
}
