package com.example.crud.controller;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.Category;
import com.example.crud.entity.Product;
import com.example.crud.service.CategoryService;
import com.example.crud.service.JwtService;
import com.example.crud.service.ProductService;
import com.example.crud.service.impl.ObjectImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, JwtService jwtService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.jwtService= jwtService;
    }

    //lấy danh sách tất cả sản phẩm dành cho user
    // lọc theo category, khoảng giá, search keyword
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/userPage/products")
    public DataResult<Product> findAllProduct(@RequestParam(required = false, defaultValue = "") String keyword,
                                                  @RequestParam(required = false, defaultValue = "0") double priceMin,
                                                  @RequestParam(required = false, defaultValue = "0") double priceMax,
                                                  @RequestParam(required = false, defaultValue = "0") long categoryId,
                                                  @RequestParam(required = false, defaultValue = "") String sortBy,
                                                  @RequestParam(required = false, defaultValue = "9") int limit,
                                                  @RequestParam(required = false, defaultValue = "1") int page){
            Map<String, Object> input = new HashMap<>();
            input.put(InputParam.KEY_WORD, keyword);
            input.put(InputParam.PRICE_MAX, priceMax);
            input.put(InputParam.PRICE_MIN, priceMin);
            input.put(InputParam.CATEGORY_ID, categoryId);
            input.put(InputParam.SORT_BY, sortBy);
            input.put(InputParam.LIMIT, limit);
            input.put(InputParam.PAGE, page);
            List<Product> output = new ArrayList<>();
            output= productService.filterProduct(input);
            List<Product> totalProduct= productService.findAllProduct();
            if (output == null || output.size() == 0) {
            }
            JSONObject jsonObject= new JSONObject();
            Map<String, Object> map= new HashMap<>();
            ObjectImpl object= new ObjectImpl();
            int totalPage = (output.size()) / limit + ((output.size() % limit == 0) ? 0 : 1);
            jsonObject.put(InputParam.PAGING, object.getPagingJSONObject(totalProduct.size(), limit, totalPage, page));
            int total_count=totalProduct.size();
            int record_in_page=limit;
            int total_page=(output.size())/record_in_page;
            int current_page=page;
            return  new DataResult(output, new DataPage(total_count,record_in_page,total_page,current_page), HttpStatus.OK);

    }

//    public JSONObject convertProductToJSONObject(Product product){
//        JSONObject jsonObject= new JSONObject();
//        jsonObject.put("product_id", product.getId());
//        jsonObject.put("cate_id", product.getCategory());
//        jsonObject.put("name", product.getName());
//        jsonObject.put("price", product.getPrice());
//        jsonObject.put("description", product.getDescription());
//        jsonObject.put("preview", product.getPreview());
//        jsonObject.put("dateAdd", product.getDateAdd());
//        jsonObject.put("image", product.getImage());
//        return jsonObject;
//    }



    // xem chi tiết 1 sản phẩm
    @CrossOrigin
    @GetMapping(value = "products/{id}")
    public ResponseEntity<Product> getAProduct(@PathVariable("id") long productId) {
        try{
            Product currentProduct = productService.findById(productId);
            if (currentProduct != null) {
                return new ResponseEntity(currentProduct, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            logger.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // xóa 1 sản phẩm
    @CrossOrigin
    @DeleteMapping(value = "products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") long productId) {
        Product product = productService.findById(productId);
        if (product == null) {
            return new ResponseEntity("ProductId is not exist", HttpStatus.NOT_FOUND);
        } else {
            productService.remove(product);
            return new ResponseEntity("Success", HttpStatus.OK);
        }

    }

    @CrossOrigin
    @GetMapping(value = "products/sort")
    public ResponseEntity<Product> findProductByKeyword(@RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
                                                        @RequestParam(value = "limit", required = false, defaultValue = "15") int limit) {
        List<Product> productList = productService.filterByDateAdd(limit, sortBy);
        if (productList != null && productList.size() > 0) {
            return new ResponseEntity(productList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/image")
    public  String getImage() throws  IOException {
            File serverFile = new File("/home/tupva/Downloads/CRUDProduct/src/main/java/com/example/crud/image/1.png");
        return  serverFile.toPath().toString();
    }

    //---------------------------------------ADMIN--------------------------------------------------

    //lấy danh sách mặt hàng cho admin xem
    // lọc theo keyword, khoảng giá, categoryId, ngày thêm sản phẩm, sắp xếp sản phẩm bán chạy/ bán ế
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/adminPage/products")
    public DataResult<Product> findAllProductByAdmin(@RequestParam(required = false, defaultValue = "") String keyword,
                                              @RequestParam(required = false, defaultValue = "0") double priceMin,
                                              @RequestParam(required = false, defaultValue = "0") double priceMax,
                                              @RequestParam(required = false, defaultValue = "0") long categoryId,
                                              @RequestParam(required = false, defaultValue = "") String sortBy,
                                              @RequestParam(required = false, defaultValue = "9") int limit,
                                              @RequestParam(required = false, defaultValue = "1") int page,
                                                     HttpServletRequest request) throws JsonProcessingException {
        if(jwtService.isAdmin(request)){
            Map<String, Object> input = new HashMap<>();
            input.put(InputParam.KEY_WORD, keyword);
            input.put(InputParam.PRICE_MAX, priceMax);
            input.put(InputParam.PRICE_MIN, priceMin);
            input.put(InputParam.CATEGORY_ID, categoryId);
            input.put(InputParam.SORT_BY, sortBy);
            input.put(InputParam.LIMIT, limit);
            input.put(InputParam.PAGE, page);
            List<Product> output = new ArrayList<>();
            output= productService.filterProduct(input);
            List<Product> totalProduct= productService.findAllProduct();
            if (output == null || output.size() == 0) {
            }
            JSONObject jsonObject= new JSONObject();
            Map<String, Object> map= new HashMap<>();
            ObjectImpl object= new ObjectImpl();
            int totalPage = (output.size()) / limit + ((output.size() % limit == 0) ? 0 : 1);
            jsonObject.put(InputParam.PAGING, object.getPagingJSONObject(totalProduct.size(), limit, totalPage, page));
            int total_count=totalProduct.size();
            int record_in_page=limit;
            int total_page=(output.size())/record_in_page;
            int current_page=page;
            return  new DataResult(output, new DataPage(total_count,record_in_page,total_page,current_page), HttpStatus.OK);
        }
        return new DataResult<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    // tạo mới 1 sản phẩm
    @CrossOrigin
    @PostMapping(value = "/adminPage/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product, UriComponentsBuilder builder, HttpServletRequest request) {
        if(jwtService.isAdmin(request)) {
            long categoryId = product.getCategory().getId();
            try {
                Category category = categoryService.findById(categoryId);
                if (category == null) {
                    return new ResponseEntity("CategoryId is not exists", HttpStatus.BAD_REQUEST);
                }
                product.setCategory(category);
                product.setDateAdd(new Date().getTime());
                productService.save(product);
//                HttpHeaders httpHeaders = new HttpHeaders();
//                httpHeaders.setLocation(builder.path("/products/{id}").buildAndExpand(product.getId()).toUri());
            } catch (Exception e) {
                logger.error(String.valueOf(e));
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    // cập nhật tt 1 sp
    @CrossOrigin
    @PutMapping(value = "/adminPage/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long productId, @RequestBody Product product, HttpServletRequest request) {
        if(jwtService.isAdmin(request)){
            if(product.getId()== 0 || productId!= product.getId()){
                return new ResponseEntity("Input wrong!", HttpStatus.BAD_REQUEST);
            }
            productService.update(product);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity("You isn't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
