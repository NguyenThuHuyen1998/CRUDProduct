package com.example.crud.repository;

import com.example.crud.entity.Order;
import com.example.crud.entity.OrderLine;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
    created by HuyenNgTn on 15/11/2020
*/
@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    //get list order of each user
    @Query("select t from Order t left join fetch t.user tcc where tcc.userId = :userId")
    List<Order> getListOrderByUserId(@Param("userId") long userId);

    @Query("select t from Order t where t.status = :status")
    List<Order> getListByStatus(@Param("status") String status);

//    @Query("select t from Order t left join fetch t.orderLine tcc where tcc.productId=: productId")
//    List<Order> getListOrderByProductId(@Param("productId") long productId);


}
