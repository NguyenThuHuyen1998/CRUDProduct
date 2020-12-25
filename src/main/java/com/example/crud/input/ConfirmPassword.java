package com.example.crud.input;

public class ConfirmPassword {
    private String confirmCode;
    private String newPassword;
    private String userName;

    public ConfirmPassword(String userName, String confirmCode, String newPassword) {
        this.userName= userName;
        this.confirmCode = confirmCode;
        this.newPassword = newPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
