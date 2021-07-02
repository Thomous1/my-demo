package com.wangxiaolang.demo.security.entity;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/25 19:22
 * @Version 1.0
 */
public class ErrorMsg {
    private int errorCode;
    private String errorMsg;

    ErrorMsg(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
