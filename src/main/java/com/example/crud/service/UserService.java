package com.example.crud.service;

import com.example.crud.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/*
    created by HuyenNgTn on 15/11/2020
*/
public interface UserService {

    List<User> findAllUser();
    Optional<User> findById(Long userId);
    boolean add (User user);
    boolean delete (long userId);
    boolean checkLogin(User user);
}
