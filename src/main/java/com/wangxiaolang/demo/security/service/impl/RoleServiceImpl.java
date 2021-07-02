package com.wangxiaolang.demo.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangxiaolang.demo.security.entity.Role;
import com.wangxiaolang.demo.security.mapper.RoleMapper;
import com.wangxiaolang.demo.security.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/29 20:23
 * @Version 1.0
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Set<String> findPermissions(String username) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        List<Role> list = this.baseMapper.selectList(queryWrapper);
        Set<String> permissions = new HashSet<>();
        list.forEach(r->permissions.add(r.getRole()));
        return permissions;
    }
}
