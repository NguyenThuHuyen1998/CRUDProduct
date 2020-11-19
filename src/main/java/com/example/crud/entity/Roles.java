package com.example.crud.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;

/*
    created by HuyenNgTn on 15/11/2020
*/
@Entity
@Table(name = "tblROLES")
public class  Roles implements Serializable {

    @Id
    @Column(name = "role_id")
    private long roleId;

    @Column(name = "role")
    private String role;

    public Roles() {
    }

    public Roles(long roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
