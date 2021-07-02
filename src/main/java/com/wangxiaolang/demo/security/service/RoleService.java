package com.wangxiaolang.demo.security.service;

import java.util.Set;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/29 20:22
 * @Version 1.0
 */
public interface RoleService {

    Set<String> findPermissions(String username);
}
