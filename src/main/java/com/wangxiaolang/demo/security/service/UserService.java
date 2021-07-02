package com.wangxiaolang.demo.security.service;

import com.wangxiaolang.demo.security.entity.User;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/25 19:32
 * @Version 1.0
 */
public interface UserService {

     User findByUsername(String username);

}
