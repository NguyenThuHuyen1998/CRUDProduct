package com.example.crud.service;


import com.example.crud.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAllCategory();
    Category findById(Long categoryId);
    void save (Category category);
    void remove (Category category);
}
