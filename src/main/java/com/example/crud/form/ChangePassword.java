package com.example.crud.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    created by HuyenNgTn on 12/12/2020
*/
public class ChangePassword {
    public static final Logger logger = LoggerFactory.getLogger(ChangePassword.class);
    private String oldPass;
    private String newPass;
    private String rePass;

    public ChangePassword(String oldPass, String newPass, String rePass) {
        this.oldPass = oldPass;
        this.newPass = newPass;
        this.rePass = rePass;
    }

    public boolean validatePassword(){
        return newPass.equals(rePass) ? true: false;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getRePass() {
        return rePass;
    }

    public void setRePass(String rePass) {
        this.rePass = rePass;
    }
}
