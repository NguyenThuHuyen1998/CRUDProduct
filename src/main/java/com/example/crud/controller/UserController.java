package com.example.crud.controller;

import com.example.crud.entity.Order;
import com.example.crud.form.ChangePassword;
import com.example.crud.predicate.PredicateOrderFilter;
import com.example.crud.service.OrderService;
import com.example.crud.service.impl.JwtServiceImpl;
import com.example.crud.constants.InputParam;
import com.example.crud.entity.User;
import com.example.crud.jwt.JwtTokenProvider;
import com.example.crud.service.CartService;
import com.example.crud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Predicate;

/*

    created by HuyenNgTn on 15/11/2020
*/
@RestController
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private JwtServiceImpl jwtHandler;
    private UserService userService;
    private CartService cartService;
    private OrderService orderService;
    private static PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    public UserController(UserService userService, CartService cartService, OrderService orderService, JwtServiceImpl jwtHandler, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.jwtHandler = jwtHandler;
        this.passwordEncoder= passwordEncoder;
    }

    // xem thông tin cá nhân user
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Object> getDetailUser(HttpServletRequest request) {
        if (jwtHandler.isUser(request)) {
            long userId= jwtHandler.getCurrentUser(request).getUserId();
            User user = userService.findById(userId);
            return new ResponseEntity<Object>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
    }


    //Thay đổi mật khẩu người dùng
    @PutMapping(value = "/user/changePassword")
    public ResponseEntity<User> changePassword(@RequestBody ChangePassword data, HttpServletRequest request) {
        if(jwtHandler.isUser(request)){
            try {
                long userId = jwtHandler.getCurrentUser(request).getUserId();
                User user = userService.findById(userId);
                passwordEncoder= new BCryptPasswordEncoder();
                if(passwordEncoder.matches(data.getOldPass(), user.getPassword()) && data.validatePassword()){
                    user.setPassword(passwordEncoder.encode(data.getNewPass()));
                    userService.add(user);
                    return new ResponseEntity("Success", HttpStatus.OK);
                }
                return new ResponseEntity("Not Found User", HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
    }



    //-------------------------------------------ADMIN---------------------------------------------
    //xem danh sách user hiện có và trạng thái hoạt động
    @GetMapping(value = "/adminPage/users")
    public ResponseEntity<User> getAllUser(HttpServletRequest request) {
        if (jwtHandler.isAdmin(request)) {
            List<User> userList = userService.findAllUser();
            if (userList == null || userList.size() == 0) {
                logger.error("User list empty");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(userList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @DeleteMapping(value = "/adminPage/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(name = "id") long userId, HttpServletRequest request) {
        if (jwtHandler.isAdmin(request)) {
            User user = userService.findById(userId);
            Predicate<Order> predicate = null;
            PredicateOrderFilter predicateOrderFilter = PredicateOrderFilter.getInstance();
            Predicate<Order> checkUser = predicateOrderFilter.checkUser(userId);
            Predicate<Order> checkStatusProcessing = predicateOrderFilter.checkStatus(InputParam.PROCESSING);
            predicate = checkUser.and(checkStatusProcessing);
            List<Order> orderList = predicateOrderFilter.filterOrder(orderService.findAllOrder(), predicate);
            for (Order order : orderList) {
                orderService.remove(order);
            }

            userService.delete(user);
            return new ResponseEntity("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    /* ---------------- không xóa user, chỉ vô hiệu hóa ------------------------ ADMIN*/
//    @RequestMapping(value = "/adminPage/users/{id}", method = RequestMethod.DELETE)
//    public ResponseEntity<String> disableUser(@PathVariable("id") long id) {
//        User user= userService.findById(id);
//        if(user!= null){
//            userService.add(user);
//            return new ResponseEntity<String>("Disable!", HttpStatus.OK);
//        }
//        return new ResponseEntity<>("User not exist", HttpStatus.BAD_REQUEST);
//
//    }


    // phân quyền user làm admin
    @PostMapping(value = "/adminPage/decentralization/{id}")
    public ResponseEntity<User> decentralizationAdmin(@PathVariable("id") long id, HttpServletRequest request) {
        if (jwtHandler.isAdmin(request)) {
            User user = userService.findById(id);
            if (user == null) {
                return new ResponseEntity("User yet has not been register", HttpStatus.BAD_REQUEST);
            } else {
                user.setRole(InputParam.ADMIN);
                userService.update(user);
                return new ResponseEntity(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }
}

