package com.wangxiaolang.demo.security.controller;

import com.wangxiaolang.demo.security.entity.LoginBean;
import com.wangxiaolang.demo.security.entity.ResultModel;
import com.wangxiaolang.demo.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/25 19:04
 * @Version 1.0
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public ResultModel login(@RequestBody LoginBean loginBean, HttpServletRequest request) {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();
        // 系统登录认证
        String token = SecurityUtils.login(request, username, password, authenticationManager);
        return ResultModel.ok(token);
    }
}
