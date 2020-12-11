package com.example.crud.service;

import com.example.crud.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

/*
    created by HuyenNgTn on 15/11/2020
*/
public interface UserService {

    List<User> findAllUser();
    User findById(Long userId);
    User findByName(String userName);
    boolean add (User user);
    void update(User user);
    void delete (User user);
    boolean checkLogin(User user);
    public UserDetails loadUserById(Long id);
    public UserDetails loadUserByUsername(String username);
}
