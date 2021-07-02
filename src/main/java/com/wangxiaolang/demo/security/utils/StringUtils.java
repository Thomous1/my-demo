package com.wangxiaolang.demo.security.utils;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/28 10:12
 * @Version 1.0
 */
public class StringUtils {

    public static boolean isNotBlank(String str) {
        return ! (str.isEmpty() || str == "") ;
    }
}
