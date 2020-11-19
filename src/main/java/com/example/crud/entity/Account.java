//package com.example.crud.entity;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
///*
//    created by HuyenNgTn on 15/11/2020
//*/
//@Entity
//@Table(name = "accounts")
//public class Account implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @Column(name = "user-name")
//    private String userName;
//
//    @Column(name = "role")
//    private String role;
//
//    @Column(name= "active")
//    private boolean active;
//
//    public Account() {
//    }
//
//    public Account(String userName, String role, boolean active) {
//        this.userName = userName;
//        this.role = role;
//        this.active = active;
//    }
//
//    public Account(String userName, String role) {
//        this.userName = userName;
//        this.role = role;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public boolean isActive() {
//        return active;
//    }
//
//    public void setActive(boolean active) {
//        this.active = active;
//    }
//}
