package com.example.crud.controller;

import com.example.crud.entity.LoginUser;
import com.example.crud.entity.User;
import com.example.crud.security.AuthorizationWithToken;
import com.example.crud.service.UserService;
import com.nimbusds.jose.util.Base64URL;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.minidev.json.parser.JSONParser;
import org.hibernate.hql.internal.ast.util.TokenPrinters;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

/*
    created by HuyenNgTn on 18/11/2020
*/
@RestController
public class LoginController {
    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService= userService ;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginUser loginUser){
        AuthorizationWithToken authorizationWithToken= new AuthorizationWithToken();
        String token= authorizationWithToken.checkLogin(loginUser.getUserName(), loginUser.getPassword());
        if(token.equals("")){
            return new ResponseEntity<>("Username or password is wrong", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody User user){
        AuthorizationWithToken authorizationWithToken= new AuthorizationWithToken();
        String purePassword= user.getPassword();
        user.setPassword(authorizationWithToken.generatedMD5(purePassword));
        userService.add(user);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }
}
