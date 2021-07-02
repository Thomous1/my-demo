package com.wangxiaolang.demo.security.utils;

import com.wangxiaolang.demo.security.Security.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/25 19:17
 * @Version 1.0
 */
public class SecurityUtils {
    /* *
     * @Description 系统登录认证
     * @Date 19:25 2021/6/25
     * @Param [request, username, password, authenticationManager]
     * @return string token
     **/
    public static String login(HttpServletRequest request, String username, String password, AuthenticationManager authenticationManager) {
        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(username, password);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 执行登录认证过程
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 认证成功存储认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌并返回给客户端
        String token = JwtTokenUtils.generateToken(authentication);
        authenticationToken.setToken(token);
        return token;
    }

    /**
     * 获取令牌进行认证
     * @param request
     */
    public static void checkAuthentication(HttpServletRequest request) {
        // 获取令牌并根据令牌获取登录认证信息
        Authentication authentication = JwtTokenUtils.getAuthenticationeFromToken(request);
        // 设置登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 获取当前用户名
     * @return
     */
    public static String getUsername() {
        String username = null;
        Authentication authentication = getAuthentication();
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            if(principal != null && principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }

    /**
     * 获取用户名
     * @return
     */
    public static String getUsername(Authentication authentication) {
        String username = null;
        if(authentication != null) {
            Object principal = authentication.getPrincipal();
            if(principal != null && principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }

    /**
     * 获取当前登录信息
     * @return
     */
    public static Authentication getAuthentication() {
        if(SecurityContextHolder.getContext() == null) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }
}
