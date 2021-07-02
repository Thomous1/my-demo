package com.wangxiaolang.demo.security.controller;

import com.wangxiaolang.demo.security.entity.ResultModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/25 19:44
 * @Version 1.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value="/findAll")
    public ResultModel findAll() {
        return ResultModel.ok("the findAll service is called success.");
    }

    @PreAuthorize("hasAuthority('sys:user:edit')")
    @GetMapping(value="/edit")
    public ResultModel edit() {
        return ResultModel.ok("the edit service is called success.");
    }

    @PreAuthorize("hasAuthority('sys:user:delete')")
    @GetMapping(value="/delete")
    public ResultModel delete() {
        return ResultModel.ok("the delete service is called success.");
    }
}
