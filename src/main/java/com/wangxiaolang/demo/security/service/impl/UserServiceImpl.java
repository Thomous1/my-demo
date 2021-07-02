package com.wangxiaolang.demo.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangxiaolang.demo.security.mapper.UserMapper;
import com.wangxiaolang.demo.security.service.UserService;
import com.wangxiaolang.demo.security.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/25 19:35
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User findByUsername(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user == null)
            return null;
        String password = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(password);
        return user;
    }


}
