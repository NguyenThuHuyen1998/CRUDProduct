package com.example.crud.controller;

import com.example.crud.entity.Category;
import com.example.crud.entity.Product;
import com.example.crud.service.CategoryService;
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
    private CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService){
        this.productService= productService;
        this.categoryService= categoryService;
    }

    //lấy danh sách tất cả sản phẩm
    @GetMapping(value = "/products")
    public ResponseEntity<Product> findAllProduct(){
        List<Product> productList= productService.findAllProduct();
        if(productList== null || productList.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(productList, HttpStatus.OK);
    }

    // tạo mới 1 sản phẩm
    @PostMapping(value = "/products")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product, UriComponentsBuilder builder){
        long categoryId= product.getCategory().getId();
        Optional<Category> category= categoryService.findById(categoryId);
        if(category==null){
            return new ResponseEntity("CategoryId is not exists", HttpStatus.BAD_REQUEST);
        }
        product.setCategory(category.get());
        productService.save(product);
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setLocation(builder.path("/products/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // cập nhật tt 1 sp
    @PutMapping(value = "products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable ("id") long productId, @RequestBody Product product){
        Optional<Product> currentProduct= productService.findById(productId);
        if(currentProduct.isEmpty()){
            return new ResponseEntity("Product is not exist", HttpStatus.NOT_FOUND);
        }
        if(product== null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            currentProduct.get().setCategory(product.getCategory());
            currentProduct.get().setName(product.getName());
            currentProduct.get().setPrice(product.getPrice());
            currentProduct.get().setDescription(product.getDescription());
            productService.save(currentProduct.get());
            return new ResponseEntity("Success", HttpStatus.OK);
        }
    }

    // xem chi tiết 1 sản phẩm
    @GetMapping(value = "products/{id}")
    public ResponseEntity<Product> getAProduct(@PathVariable ("id") long productId){
        Optional<Product> currentProduct= productService.findById(productId);
        if(currentProduct== null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(currentProduct, HttpStatus.OK);

    }

    // xóa 1 sản phẩm
    @DeleteMapping(value = "products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable ("id") long productId){
        Optional<Product> product= productService.findById(productId);
        if(product.isEmpty()){
            return new ResponseEntity("ProductId is not exist", HttpStatus.NOT_FOUND);
        }
        else {
            productService.remove(product.get());
            return new ResponseEntity("Success", HttpStatus.OK);
        }

    }

    // lấy danh sách sản phẩm theo phân loại danh mục
    @GetMapping(value = "products/cate/{id}")
    public ResponseEntity<Product> findProductByCategory(@PathVariable ("id") long categoryId){
        Optional<Category> category= categoryService.findById(categoryId);
        if(category.isEmpty()){
            return new ResponseEntity("CategoryId is not exist", HttpStatus.NOT_FOUND);
        }
        List<Product> products= productService.findByCategoryID(categoryId);
        if(products.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(products, HttpStatus.OK);

    }
}
