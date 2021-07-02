package com.wangxiaolang.demo.security.utils;

import com.alibaba.fastjson.JSON;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/25 19:01
 * @Version 1.0
 */
public class HttpUtils {
    public static void write(HttpServletResponse response, Object token) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out=response.getWriter();
        out.println(JSON.toJSON(token).toString());
        out.flush();
        out.close();
    }
}
