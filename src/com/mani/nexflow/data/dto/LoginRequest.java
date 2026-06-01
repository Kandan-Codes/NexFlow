package com.mani.nexflow.data.dto;

public class LoginRequest {

    private String email;
    private String accessKey;

    public LoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return accessKey;
    }

    public void setPassword(String accessKey) {
        this.accessKey = accessKey;
    }
}
