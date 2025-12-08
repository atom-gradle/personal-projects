package com.atom.personnel.dto.request;

import java.time.LocalDateTime;

public class LoginRequest {

    private String username;
    private String password;
    //ON or OFF
    private String autoLogin;
    private String ipAddress;
    private String province;
    private String areaLat;
    private String areaLng;
    private LocalDateTime lastLoginTime;
    private String UserAgent;

    public LoginRequest() {}

    public LoginRequest(String username, String password, String autoLogin, String ipAddress, String province, String areaLat, String areaLng, String lastLoginTime, String userAgent) {
        this.username = username;
        this.password = password;
        this.autoLogin = autoLogin;
        this.ipAddress = ipAddress;
        this.province = province;
        this.areaLat = areaLat;
        this.areaLng = areaLng;
        this.lastLoginTime = LocalDateTime.parse(lastLoginTime);
        UserAgent = userAgent;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAutoLogin() {
        return autoLogin;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getProvince() {
        return province;
    }

    public String getAreaLat() {
        return areaLat;
    }

    public String getAreaLng() {
        return areaLng;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", hashedPassword='" + password + '\'' +
                ", autoLogin='" + autoLogin + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", province='" + province + '\'' +
                ", areaLat='" + areaLat + '\'' +
                ", areaLng='" + areaLng + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", UserAgent='" + UserAgent + '\'' +
                '}';
    }
}
