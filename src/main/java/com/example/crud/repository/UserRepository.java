package com.example.crud.repository;

import com.example.crud.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/*
    created by HuyenNgTn on 15/11/2020
*/
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
//
//    @Query("select t from User t left join fetch t.account tcc where tcc.userName = :userName")
//    List<User> findUserByAccount(@Param("userName") String userName);

    @Query("delete from User where userId =: userId")
    void delete(@Param("userId") long userId);
}
