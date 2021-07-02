package com.wangxiaolang.demo.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangxiaolang.demo.security.entity.ResultModel;
import com.wangxiaolang.demo.security.Security.JwtAuthenticationToken;
import com.wangxiaolang.demo.security.utils.HttpUtils;
import com.wangxiaolang.demo.security.utils.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @Author wangzuoyu1
 * @Description 登录认证的过滤器
 * @Date 2021/6/25 18:24
 * @Version 1.0
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        // 请求 /login 登录时拦截， 由此方法触发执行登录认证流程
        super.doFilter(req, res, chain);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String body = getBody(request);
        JSONObject jsonObject = JSON.parseObject(body);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        username = username == null ? "" : username.trim();
        password = password == null ? "" : password;

        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        // 存储登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authResult);
        // 记住我服务
        getRememberMeServices().loginSuccess(request, response, authResult);
        // 触发事件监听器
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }
        // 生成并返回token给客户端，后续访问携带此token
        String token  = JwtTokenUtils.generateToken(authResult);
        new JwtAuthenticationToken(authResult.getPrincipal(), authResult.getCredentials(), token);
        ResultModel resultModel = new ResultModel();
        resultModel.setData(token);
        HttpUtils.write(response, resultModel);
    }

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public String getBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
