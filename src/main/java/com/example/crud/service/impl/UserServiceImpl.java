package com.example.crud.service.impl;

import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;
import com.example.crud.service.UserService;
import com.example.crud.entity.CustomUserDetails;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.slf4j.LoggerFactory.*;

/*
    created by HuyenNgTn on 15/11/2020
*/
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    public static final Logger logger = getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUser() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.get();
    }

    @Override
    public User findByName(String userName) {
        List<User> userList = findAllUser();
        for (User user : userList) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        logger.error("UserName not exist");
        return null;
    }

    @Override
    public boolean add(User user) {
        List<User> userList = findAllUser();
        if (userList != null && userList.size() > 0) {
            for (User userExist : userList) {
                if (userExist.getUserName().equals(user.getUserName()) || userExist.getEmail().equals(user.getEmail())) {
                    return false;
                }
            }
        }
        userRepository.save(user);
        return true;
    }


    @Override
    public void delete(User user) {
        if (user != null) {
            userRepository.delete(user);
        }
    }

    public boolean checkLogin(User user) {
        List<User> listUser = (List<User>) userRepository.findAll();
        for (User userExist : listUser) {
            if (StringUtils.equals(user.getUserName(), userExist.getUserName())
                    && StringUtils.equals(user.getPassword(), userExist.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    // JWTAuthenticationFilter sẽ sử dụng hàm này
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }

}
