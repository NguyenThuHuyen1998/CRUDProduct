package com.example.crud.controller;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.Cart;
import com.example.crud.entity.User;
import com.example.crud.form.LoginRequest;
import com.example.crud.form.LoginResponse;
import com.example.crud.form.RandomStuff;
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

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Xác thực thông tin người dùng Request lên
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }

    // Api /api/random yêu cầu phải xác thực mới có thể request
    @GetMapping("/random")
    public RandomStuff randomStuff(){
        return new RandomStuff("JWT Hợp lệ mới có thể thấy được message này");
    }


    @GetMapping(value = "/users")
    public ResponseEntity<User> getAllUser(){
        List<User> userList= userService.findAllUser();
        if(userList== null|| userList.size()== 0){
            logger.error("User list empty");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(userList, HttpStatus.OK);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        user.setEnable(true);
        userService.add(user);
        cartService.save(new Cart(user));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


        @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
        public ResponseEntity<Object> getUserById(@PathVariable("id") long id) {
            User user = userService.findById(id);
            if (user != null) {
                return new ResponseEntity<Object>(user, HttpStatus.OK);
            }
            return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
        }

        /* ---------------- không xóa user, chỉ vô hiệu hóa ------------------------ */
        @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
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
    @PostMapping(value = "user/admin/{id}")
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
//        @RequestMapping(value = "/login", method = RequestMethod.POST)
//        public ResponseEntity<String> login(HttpServletRequest request, @RequestBody User user) {
//            String result = "";
//            HttpStatus httpStatus = null;
//            try {
////                if (userService.checkLogin(user)) {
////                    result = jwtServiceImpl.generateTokenLogin(user.getUserName());
////                    httpStatus = HttpStatus.OK;
////                } else {
////                    result = "Wrong userId and password";
////                    httpStatus = HttpStatus.BAD_REQUEST;
////                }
//            } catch (Exception ex) {
//                result = "Server Error";
//                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//            }
//            return new ResponseEntity<String>(result, httpStatus);
//        }



        public boolean checkEnableUser(long userId){
            User user= userService.findById(userId);
            return user.isEnable();
        }
    }

