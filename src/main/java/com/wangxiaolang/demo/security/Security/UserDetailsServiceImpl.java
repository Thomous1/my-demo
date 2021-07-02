package com.wangxiaolang.demo.security.Security;

import com.wangxiaolang.demo.security.service.UserService;
import com.wangxiaolang.demo.security.entity.User;
import com.wangxiaolang.demo.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/25 19:28
 * @Version 1.0
 */

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        Set<String> permissions = roleService.findPermissions(username);
        List<GrantedAuthority> grantedAuthorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new JwtUserDetails(username, user.getPassword(), grantedAuthorities);
    }
}
