package com.example.crud.controller;

import com.example.crud.entity.Category;
import com.example.crud.service.CategoryService;
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
@RequestMapping(produces = {"application/json; charset=UTF-8","*/*;charset=UTF-8"})
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService= categoryService;
    }

//    produces={"application/json; charset=UTF-8"}
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<Category> getAll(){
        List<Category> categoryList= categoryService.findAllCategory();
        if(categoryList== null|| categoryList.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(categoryList, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public ResponseEntity<Category> postCategory(@RequestBody Category category, UriComponentsBuilder builder){
        categoryService.save(category);
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setLocation(builder.path("/categories/{id}").buildAndExpand(category.getId()).toUri());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategory(@PathVariable long categoryId){
        Optional<Category> category= categoryService.findById(categoryId);
        if(category== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(category, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Category> updateCategory(@PathVariable("id") long categoryId, @RequestBody Category category){
        Optional<Category> currentCategory= categoryService.findById(categoryId);
        if(category== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        currentCategory.get().setDescription(category.getDescription());
        currentCategory.get().setName(category.getName());
        return new ResponseEntity(currentCategory, HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") long categoryId){
        Optional<Category> currentCategory= categoryService.findById(categoryId);
        if(currentCategory== null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        categoryService.remove(currentCategory.get());
        return new ResponseEntity(currentCategory, HttpStatus.OK);
    }
}
