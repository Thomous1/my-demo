package com.wangxiaolang.demo.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/18 18:22
 * @Version 1.0
 */
@Configuration
public class caffeineConfig {

    /**
     * 创建基于Caffeine的Cache Manager
     *
     * @return
     */
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caches = Lists.newArrayList();
        Map<String, Object> map = getCacheType();
        for (String name : map.keySet()) {
            caches.add(new CaffeineCache(name, (Cache<Object, Object>) map.get(name)));
        }
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    /**
     * 初始化自定义缓存策略
     *
     * @return
     */
    private static Map<String, Object> getCacheType() {
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("testCaffeine", Caffeine.newBuilder().recordStats()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(130000)
                .build());
        return map;
    }

}
