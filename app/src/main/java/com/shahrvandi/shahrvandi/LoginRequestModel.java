package com.shahrvandi.shahrvandi;

import com.google.gson.annotations.SerializedName;

public class LoginRequestModel extends BasicRequestModel {

    public LoginRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @SerializedName("UserName")
    private String username;

    @SerializedName("UserPassword")
    private String password;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
