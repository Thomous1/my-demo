package com.wangxiaolang.demo.cache.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/21 13:04
 * @Version 1.0
 */
@Service
public class CaffeineCacheServiceImpl implements CaffeineCacheService {

    @Autowired
    CacheManager caffeineCacheManager;

    private final static String DEFAULT_CACHE = "testCaffeine";

    public <T> T getValue(Object key) {
        if(key == null) return null;

        Cache cache = caffeineCacheManager.getCache(DEFAULT_CACHE);
        if(cache != null) {
            Cache.ValueWrapper wrapper = cache.get(key);
            if (wrapper != null)
                return (T) wrapper.get();
        }

        return null;
    }

    public <T> T getValue(String cacheName, Object key) {
        if(cacheName == null || key == null) return null;

        Cache cache = caffeineCacheManager.getCache(cacheName);
        if(cache != null) {
            Cache.ValueWrapper wrapper = cache.get(key);
            if (wrapper != null)
                return (T) wrapper.get();
        }

        return null;
    }

    public void putValue(Object key, Object value) {
        if(key == null || value == null) return;

        Cache cache = caffeineCacheManager.getCache(DEFAULT_CACHE);
        if(cache != null) {
            cache.put(key, value);
        }
    }

    public void putValue(String cacheName, Object key, Object value) {
        if(cacheName == null || key == null || value == null) return;

        Cache cache = caffeineCacheManager.getCache(cacheName);
        if(cache != null) {
            cache.put(key, value);
        }
    }
}
