package com.wangxiaolang.demo.security.entity;


import java.io.Serializable;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/25 19:19
 * @Version 1.0
 */
public class ResultModel<T> implements Serializable {
    private static final long serialVersionUID = -31085742939234526L;
    public static final Integer ERROR = 1;
    public static final Integer SUCCESS = 0;
    private Integer code;
    private String message;
    private T data;
    private long timestamp;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ResultModel() {
        this.code = SUCCESS;
        this.message= "success";
        this.timestamp = System.currentTimeMillis();
    }
    public ResultModel(T data) {
        super();
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    public ResultModel(ErrorMsg error) {
        this.code = error.getErrorCode();
        this.message= error.getErrorMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public ResultModel(String msg) {
        this.code = ERROR;
        this.message= msg;
        this.timestamp = System.currentTimeMillis();
    }

    public ResultModel(Integer code, String msg) {
        this.code = code;
        this.message= msg;
        this.timestamp = System.currentTimeMillis();
    }



    public static <T> ResultModel<T> ok(T data){
        ResultModel<T> rj = new ResultModel<>();
        rj.code = SUCCESS;
        rj.message= "success";
        rj.timestamp = System.currentTimeMillis();
        rj.data = data;
        return rj;
    }

    public static ResultModel ok(){
        ResultModel rj = new ResultModel();
        rj.code = SUCCESS;
        rj.message= "success";
        rj.timestamp = System.currentTimeMillis();
        return rj;
    }
    public static ResultModel error(String msg){
        ResultModel rj = new ResultModel();
        rj.code = ERROR;
        rj.message= msg;
        rj.timestamp = System.currentTimeMillis();
        return rj;
    }
    public static <T> ResultModel<T> error(T data){
        ResultModel rj = new ResultModel();
        rj.code = ERROR;
        rj.data = data;
        rj.timestamp = System.currentTimeMillis();
        return rj;
    }
    public static <T> ResultModel<T> error(String msg,T data){
        ResultModel rj = new ResultModel();
        rj.code = ERROR;
        rj.message= msg;
        rj.data = data;
        rj.timestamp = System.currentTimeMillis();
        return rj;
    }
    public static ResultModel error(int code, String msg){
        ResultModel rj = new ResultModel();
        rj.code = code;
        rj.message= msg;
        rj.timestamp = System.currentTimeMillis();
        return rj;
    }
}
