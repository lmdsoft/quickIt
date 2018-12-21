package com.lmdsoft.modules.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern pattern
     */
    @SuppressWarnings("unchecked")
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key key
     */
    @SuppressWarnings("unchecked")
    private void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key key
     * @return true or false
     */
    @SuppressWarnings("unchecked")
    private boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key key
     * @return key 对应的Object
     */
    @SuppressWarnings("unchecked")
    public Object get(final String key) {
        Object result;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key key
     * @return key对应的value
     */
    @SuppressWarnings("unchecked")
    public String getString(final String key) {
        String result;
        ValueOperations<Serializable, String> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 模糊查询
     *
     * @param pattern pattern
     * @return 缓存列表
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> fuzzyQuery(String pattern, Class<T> t) {
        Set<String> keys = redisTemplate.keys("*" + pattern + "*");
        List<T> result = new ArrayList<T>();
        for (String key : keys) {
            result.add((T) get(key));
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key key
     * @param value value
     * @return true or false
     */
    @SuppressWarnings("unchecked")
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key key
     * @param value value
     * @return true or false
     */
    @SuppressWarnings("unchecked")
    public boolean setString(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     * @param key key
     * @param value value
     * @param expireTime 过期时间
     * @return true or false
     */
    @SuppressWarnings("unchecked")
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     * @param key key
     * @param value value
     * @param expireTime 过期时间
     * @return 如果redis存在该key值对应的value 则返回，否则返回null
     */
    @SuppressWarnings("unchecked")
    public String getAndSet(final String key, String value, Long expireTime) {
        String result = "";
        try {
            ValueOperations<Serializable, String> operations = redisTemplate.opsForValue();
            result = operations.getAndSet(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
