package com.example.crud.service.impl;

import com.example.crud.entity.Cart;
import com.example.crud.entity.User;
import com.example.crud.repository.CartRepository;
import com.example.crud.repository.UserRepository;
import com.example.crud.service.CartService;
import com.example.crud.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
    created by HuyenNgTn on 15/11/2020
*/
@Service
public class UserServiceImpl implements UserService {
    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;
    private CartRepository cartRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CartRepository cartRepository){
        this.userRepository= userRepository;
        this.cartRepository= cartRepository;
    }

    @Override
    public List<User> findAllUser() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User findByName(String userName) {
        List<User> userList= findAllUser();
        for(User user: userList){
            if(user.getUserName().equals(userName)){
                return user;
            }
        }
        logger.error("UserName not exist");
        return null;
    }
//
//    @Override
//    public List<User> findByAccount(String account) {
//        return userRepository.findUserByAccount(account);
//    }
//
//    @Override
//    public List<User> findByRole(Role role) {
//        return userRepository.findUserByRole(role);
//    }

    @Override
    public boolean add(User user) {
        List<User> userList= findAllUser();
        if(userList!= null && userList.size()>0){
            for(User userExist: userList){
                if(userExist.getUserName().equals(user.getUserName()) || userExist.getEmail().equals(user.getEmail())){
                    return false;
                }
            }
        }
        userRepository.save(user);
        return true;
    }


    @Override
    public void delete(User user) {
        if(user!= null){
            Cart cart= user.getCart();
            if(cart!= null){
                cartRepository.delete(cart);
            }
            userRepository.delete(user);
        }
    }


    public boolean checkLogin(User user) {
        List<User> listUser= (List<User>) userRepository.findAll();
        for (User userExist : listUser) {
            if (StringUtils.equals(user.getUserName(), userExist.getUserName())
                    && StringUtils.equals(user.getPassword(), userExist.getPassword())) {
                return true;
            }
        }
        return false;
    }

}
