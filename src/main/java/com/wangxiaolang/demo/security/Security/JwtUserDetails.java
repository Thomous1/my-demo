package com.wangxiaolang.demo.security.Security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/25 19:35
 * @Version 1.0
 */
public class JwtUserDetails extends User {
    private static final long serialVersionUID = 1L;

    public JwtUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(username, password, true, true, true, true, authorities);
    }

    public JwtUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
                          boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
