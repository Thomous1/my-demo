package com.wangxiaolang.demo.security.Security;

import com.wangxiaolang.demo.security.utils.JwtTokenUtils;
import com.wangxiaolang.demo.security.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/25 18:44
 * @Version 1.0
 */
@Slf4j
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public JwtAuthenticationToken(Object principal, Object credentials, List<GrantedAuthority> authorities, String token) {
        super(principal, credentials, authorities);
        this.setToken(token);
    }

    public JwtAuthenticationToken(Object principal, Object credentials, String token) {
        super(principal, credentials);
        this.setToken(token);
    }

    public void setToken(String token) {
        String username = JwtTokenUtils.getUsernameFromToken(token);
        log.info("checking authentication user " + username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            if (JwtTokenUtils.validateToken(token, username)) {
                UserDetailsServiceImpl userDetailsService = SpringContextUtil.getBean(UserDetailsServiceImpl.class);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(userDetails);
                log.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                // 用户不合法，清除上下文
                SecurityContextHolder.clearContext();
            }
        }
    }
}
