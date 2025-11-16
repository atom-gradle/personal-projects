package com.ganzhi.util;

public enum AppHttpCodeEnum {

    SYSTEM_ERROR(400,"出现错误"),
    SUCCESS(200,"操作成功"),
    CREATED(201,"创建成功");

    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}