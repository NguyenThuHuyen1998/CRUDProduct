package com.example.crud.controller;

import com.example.crud.entity.LoginUser;
import com.example.crud.entity.User;
import com.example.crud.service.UserService;
import com.nimbusds.jose.util.Base64URL;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64UrlCodec;
import org.hibernate.hql.internal.ast.util.TokenPrinters;
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

//    public LoginController()
//    @PostMapping(value = "/authenticate")
//    public ResponseEntity<String> authorize(@RequestBody LoginUser loginUser){
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword());
//        Authentication authentication= usernamePasswordAuthenticationToken
//    }


}
