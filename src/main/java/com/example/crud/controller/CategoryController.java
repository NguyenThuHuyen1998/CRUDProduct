package com.example.crud.controller;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.Category;
import com.example.crud.service.CategoryService;
import com.example.crud.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(produces = {"application/json; charset=UTF-8","*/*;charset=UTF-8"})
public class CategoryController {

    private CategoryService categoryService;
    private JwtService jwtService;

    @Autowired
    public CategoryController(CategoryService categoryService, JwtService jwtService){
        this.categoryService= categoryService;
        this.jwtService= jwtService;
    }

//    produces={"application/json; charset=UTF-8"}
    @CrossOrigin
    @GetMapping(value = "/adminPage/categories")
    public ResponseEntity<Category> getAll(HttpServletRequest request){
        if(jwtService.isAdmin(request)){
            List<Category> categoryList= categoryService.findAllCategory();
            if(categoryList== null|| categoryList.size()==0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(categoryList, HttpStatus.OK);
        }
        return new ResponseEntity("You isn't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PostMapping(value = "/adminPage/categories")
    public ResponseEntity<Category> postCategory(@RequestBody Category category, UriComponentsBuilder builder, HttpServletRequest request){
        if(jwtService.isAdmin(request)){
            categoryService.save(category);
            HttpHeaders httpHeaders= new HttpHeaders();
            httpHeaders.setLocation(builder.path("/adminPage/categories/{id}").buildAndExpand(category.getId()).toUri());
            return new ResponseEntity<>(category, HttpStatus.CREATED);
        }
        return new ResponseEntity("You isn't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping(value = "/adminPage/categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable long categoryId, HttpServletRequest request){
        if(jwtService.isAdmin(request)){
        Category category= categoryService.findById(categoryId);
        if(category== null){
            return new ResponseEntity("Category not exists", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(category, HttpStatus.OK);
        }
        return new ResponseEntity("You isn't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping(value = "/adminPage/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") long categoryId, @RequestBody Category category, HttpServletRequest request){
        if(jwtService.isAdmin(request)){
            if (category.getId()!= categoryId){
                return new ResponseEntity("Input wrong!", HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity("You isn't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(value = "/adminPage/categories/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") long categoryId,
                                                   HttpServletRequest request){
        if (jwtService.isAdmin(request)){
            Category currentCategory= categoryService.findById(categoryId);
            if(currentCategory== null){
                return new ResponseEntity("Category not exists", HttpStatus.NO_CONTENT);
            }
            categoryService.remove(currentCategory);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity("You isn't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }
}
