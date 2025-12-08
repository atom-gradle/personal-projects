package com.atom.personnel.dto.request;

public class RegisterRequest {

    private String username;
    private String phone;
    private String password;
    private String userAgent;

    public RegisterRequest() {}

    public RegisterRequest(String username, String phone, String password, String userAgent) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.userAgent = userAgent;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", hashedPassword='" + password + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
