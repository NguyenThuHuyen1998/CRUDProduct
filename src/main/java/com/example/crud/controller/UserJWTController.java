//package com.example.crud.controller;
//
//import com.example.crud.security.TokenProvider;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.validation.Valid;
//
///*
//    created by HuyenNgTn on 19/11/2020
//*/
//public class UserJWTController {
//    public static final Logger logger = LoggerFactory.getLogger(UserJWTController.class);
//
//    private final TokenProvider tokenProvider;
//
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//
//    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
//        this.tokenProvider = tokenProvider;
//        this.authenticationManagerBuilder = authenticationManagerBuilder;
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
//        String jwt = tokenProvider.createToken(authentication, rememberMe);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
//        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
//    }
//
//    /**
//     * Object to return as body in JWT Authentication.
//     */
//    static class JWTToken {
//
//        private String idToken;
//
//        JWTToken(String idToken) {
//            this.idToken = idToken;
//        }
//
//        @JsonProperty("id_token")
//        String getIdToken() {
//            return idToken;
//        }
//
//        void setIdToken(String idToken) {
//            this.idToken = idToken;
//        }
//    }
//}
