package com.wangxiaolang.demo.cache;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/7/1 18:15
 * @Version 1.0
 */
@RestController
@Api(value = "TestCaffeineController",tags = {"咖啡因测试类"})
public class TestCaffeineController {

    @Autowired
    private CacheManager cacheManager;

    @GetMapping(value = "/caffeine")
    @ApiOperation(value="获取咖啡因缓存数据",tags="获取咖啡因缓存数据",notes="nothing")
    @ApiImplicitParam(name="key",value="咖啡因缓存key值",dataType="String", paramType = "query")
    public Object caffeine(String key) {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        cacheNames.forEach(System.out::println);
        Cache cache = cacheManager.getCache("testCaffeine");
        Cache.ValueWrapper wrapper = cache.get(key);
        return wrapper.get();
    }
}
