package com.wangxiaolang.demo.cache;

import com.wangxiaolang.demo.security.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;


/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/18 18:30
 * @Version 1.0
 */

@Slf4j
@Component
public class CacheInitlizer {

    public static void init() {
        CacheManager cacheManager = SpringContextUtil.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache("testCaffeine");
        cache.put("test", "test");
        log.info("本地caffeine缓存成功~");
    }
}
