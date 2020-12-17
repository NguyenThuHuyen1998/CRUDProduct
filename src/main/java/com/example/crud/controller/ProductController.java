package com.example.crud.controller;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.Category;
import com.example.crud.entity.Product;
import com.example.crud.response.ResponseMessage;
import com.example.crud.service.CategoryService;
import com.example.crud.service.FilesStorageService;
import com.example.crud.service.JwtService;
import com.example.crud.service.ProductService;
import com.example.crud.service.impl.ObjectImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
public class ProductController {
    public static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private ProductService productService;
    private CategoryService categoryService;
    private JwtService jwtService;
    private FilesStorageService storageService;

    @Value("${file.upload-dir}")
    private String fileDir;
    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, JwtService jwtService, FilesStorageService filesStorageService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.jwtService= jwtService;
        this.storageService= filesStorageService;
    }

    //lấy danh sách tất cả sản phẩm dành cho user
    // lọc theo category, khoảng giá, search keyword
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/products")
    public ResponseEntity getAllProduct(@RequestParam(required = false, defaultValue = "") String keyword,
                                         @RequestParam(required = false, defaultValue = "-1") double priceMin,
                                         @RequestParam(required = false, defaultValue = "-1") double priceMax,
                                         @RequestParam(required = false, defaultValue = "-1") long timeStart,
                                         @RequestParam(required = false, defaultValue = "-1") long timeEnd,
                                         @RequestParam(required = false, defaultValue = "0") long categoryId,
                                         @RequestParam(required = false, defaultValue = "") String sortBy,
                                         @RequestParam(required = false, defaultValue = "9") int limit,
                                         @RequestParam(required = false, defaultValue = "1") int page){
            Map<String, Object> input = new HashMap<>();
            input.put(InputParam.KEY_WORD, keyword);
            input.put(InputParam.PRICE_MAX, priceMax);
            input.put(InputParam.PRICE_MIN, priceMin);
            input.put(InputParam.TIME_START, timeStart);
            input.put(InputParam.TIME_END, timeEnd);
            input.put(InputParam.CATEGORY_ID, categoryId);
            input.put(InputParam.SORT_BY, sortBy);;
            List<Product> output = new ArrayList<>();
            output= productService.filterProduct(input);

            List<Product> totalProduct= productService.findAllProduct();
            if (output == null || output.size() == 0) {
            }
            int totalPage = (output.size()) / limit + ((output.size() % limit == 0) ? 0 : 1);
            int total_count=totalProduct.size();
            int recordInPage=limit;
            int currentPage=page;
            Map<String, Object> paging= new HashMap<>();
            paging.put(InputParam.RECORD_IN_PAGE, recordInPage);
            paging.put(InputParam.TOTAL_COUNT, total_count);
            paging.put(InputParam.CURRENT_PAGE, currentPage);
            paging.put(InputParam.TOTAL_PAGE, totalPage);
            Map<String, Object> result= new HashMap<>();
            result.put(InputParam.PAGING, paging);
            result.put(InputParam.DATA, output);
            return new ResponseEntity(result, HttpStatus.OK);
    }


    // xem chi tiết 1 sản phẩm
    @CrossOrigin
    @GetMapping(value = "products/{id}")
    public ResponseEntity<Product> getAProduct(@PathVariable("id") long productId) {
        try{
            Product currentProduct = productService.findById(productId);
            if(!currentProduct.isActive()) {
                return new ResponseEntity("Product stopped selling", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(currentProduct, HttpStatus.OK);
        }
        catch (Exception e){
            logger.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //---------------------------------------ADMIN--------------------------------------------------


    // tạo mới 1 sản phẩm

    @CrossOrigin
    @PostMapping(value = "/adminPage/products")
    public ResponseEntity<Product> addProduct(@RequestParam("name") String name,
                                              @RequestParam("price") double price,
                                              @RequestParam("categoryId") long categoryId,
                                              @RequestParam("description") String description,
                                              @RequestParam("preview") String preview,
                                              @RequestParam("image") MultipartFile image,
                                              HttpServletRequest request) {
        if(jwtService.isAdmin(request)) {
            Product product= new Product();
            try {
                Category category = categoryService.findById(categoryId);
                product.setCategory(category);
                product.setName(name);
                product.setDateAdd(new Date().getTime());
                product.setDescription(description);
                product.setPreview(preview);
                product.setPrice(price);
                product.setActive(true);
                storageService.save(image);
                product.setImage(fileDir+ image.getOriginalFilename());
                productService.save(product);
            } catch (Exception e) {
                logger.error(String.valueOf(e));
                return new ResponseEntity( HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }
        return new ResponseEntity("You isn't admin",HttpStatus.METHOD_NOT_ALLOWED);
    }

    // cập nhật tt 1 sp
    @CrossOrigin
    @PutMapping(value = "/adminPage/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long productId, @RequestBody Product product, HttpServletRequest request) {
        if(jwtService.isAdmin(request)){
            try{
                Product currentProduct= productService.findById(productId);
                if(!currentProduct.isActive()){
                    return new ResponseEntity("Product stopped selling!!!", HttpStatus.BAD_REQUEST);
                }
                if(product.getId()== 0 || productId!= product.getId()){
                    return new ResponseEntity("Input wrong!", HttpStatus.BAD_REQUEST);
                }
                Product newProduct= productService.update(product);
                return new ResponseEntity<>(newProduct, HttpStatus.OK);
            }
            catch (Exception e){
                return new ResponseEntity("Product is not exist", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("You isn't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }


    // xóa 1 sản phẩm
    @CrossOrigin
    @DeleteMapping(value = "/adminPage/products/{id}")
    public ResponseEntity<Product> stopSelling(@PathVariable("id") long productId,
                                                 HttpServletRequest request) {
        if(jwtService.isAdmin(request)){
            try{
                Product product = productService.findById(productId);
                product.setActive(false);
                productService.save(product);
                return new ResponseEntity("Success", HttpStatus.OK);
            }
            catch (Exception e){
                logger.error(e.getMessage());
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("You isn't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }
}
