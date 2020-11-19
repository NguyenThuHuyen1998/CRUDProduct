package com.example.crud.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
    created by HuyenNgTn on 19/11/2020
*/
public class LoginUser {
    public static final Logger logger = LoggerFactory.getLogger(LoginUser.class);

    @NotNull
    @Size(min = 1, max = 50)
    private String userName;

    @NotNull
    @Size(min = 6, max = 50)
    private String password;

    private boolean rememberMe;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }
}
