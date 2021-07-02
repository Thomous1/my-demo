package com.wangxiaolang.demo.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangxiaolang.demo.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/29 20:15
 * @Version 1.0
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
