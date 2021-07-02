package com.wangxiaolang.demo.cache.service;

/**
 * @Author wangzuoyu1
 * @Description TODO
 * @Date 2021/6/21 13:07
 * @Version 1.0
 */
public interface CaffeineCacheService {
     <T> T getValue(Object key) ;

     <T> T getValue(String cacheName, Object key);

     void putValue(Object key, Object value) ;

     void putValue(String cacheName, Object key, Object value) ;
}
