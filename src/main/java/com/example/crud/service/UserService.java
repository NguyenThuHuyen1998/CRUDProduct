package com.example.crud.service;

import com.example.crud.entity.User;

import java.util.List;
import java.util.Optional;

/*
    created by HuyenNgTn on 15/11/2020
*/
public interface UserService {

    List<User> findAllUser();
    Optional<User> findById(Long userId);
    User findByName(String userName);
    boolean add (User user);
    void delete (User user);
    boolean checkLogin(User user);
}
