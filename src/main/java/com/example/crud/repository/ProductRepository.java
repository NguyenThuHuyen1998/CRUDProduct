package com.example.crud.repository;

import com.example.crud.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    @Query("select t from Product t left join fetch t.category tcc where tcc.id = :id")
    List<Product> findProductByCategoryId(@Param("id") long cateId);
}
