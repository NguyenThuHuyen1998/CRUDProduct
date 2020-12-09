package com.example.crud.controller;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.Cart;
import com.example.crud.entity.User;
import com.example.crud.jwt.JwtTokenProvider;
import com.example.crud.service.CartService;
import com.example.crud.service.UserService;
import com.example.crud.entity.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/*

    created by HuyenNgTn on 15/11/2020
*/
@RestController
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;
    private CartService cartService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    public UserController(UserService userService, CartService cartService){
        this.userService= userService;
        this.cartService= cartService;
    }

    // xem thông tin cá nhân user
    @RequestMapping(value = "/userPage/users/{id}", method = RequestMethod.GET)
        public ResponseEntity<Object> getUserById(@PathVariable("id") long id) {
            User user = userService.findById(id);
            if (user != null) {
                return new ResponseEntity<Object>(user, HttpStatus.OK);
            }
            return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
        }



        //Thay đổi mật khẩu người dùng
        @RequestMapping(value = "/userPage/users/{id}", method = RequestMethod.GET)
        public ResponseEntity<Object> changePassword(@PathVariable("id") long id) {
            User user = userService.findById(id);
            if (user != null) {
                return new ResponseEntity<Object>(user, HttpStatus.OK);
            }
            return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
        }


        public boolean checkEnableUser(long userId){
            User user= userService.findById(userId);
            return user.isEnable();
        }


        //-------------------------------------------ADMIN---------------------------------------------
        //xem danh sách user hiện có và trạng thái hoạt động
        @GetMapping(value = "/adminPage/users")
        public ResponseEntity<User> getAllUser(){
            List<User> userList= userService.findAllUser();
            if(userList== null|| userList.size()== 0){
                logger.error("User list empty");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(userList, HttpStatus.OK);
        }


    /* ---------------- không xóa user, chỉ vô hiệu hóa ------------------------ ADMIN*/
    @RequestMapping(value = "/adminPage/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> disableUser(@PathVariable("id") long id) {
        User user= userService.findById(id);
        if(user!= null){
            user.setEnable(false);
            userService.add(user);
            return new ResponseEntity<String>("Disable!", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not exist", HttpStatus.BAD_REQUEST);

    }


    // phân quyền user làm admin
    @PostMapping(value = "/adminPage/decentralization/{id}")
    public ResponseEntity<User> decentralizationAdmin(@PathVariable ("id") long id,
                                                      @RequestParam ("user-id") long userId){
        User user= userService.findById(id);
        User currentAdmin= userService.findById(id);
        if(user== null ) {
            return new ResponseEntity("User yet has not been register", HttpStatus.BAD_REQUEST);
        }
        else if(currentAdmin== null|| !currentAdmin.isEnable() || !currentAdmin.getRole().equals(InputParam.ADMIN)){
            return new ResponseEntity("You have not permission", HttpStatus.BAD_REQUEST);
        }
        else {
            user.setRole(InputParam.ADMIN);
            userService.add(user);
            return new ResponseEntity("Decentralization success", HttpStatus.OK);
        }
    }
}

