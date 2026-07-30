package com.demo.backend.common.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void setExpireTime(String key, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }   

   public long getExpireTime(String key) {
        return redisTemplate.getExpire(key);
    }

    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    public void hashSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Object hashGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
 * Redis List 左侧添加
 */
public Long leftPush(
        String key,
        Object value
){

    return redisTemplate
            .opsForList()
            .leftPush(
                    key,
                    value
            );
}


/**
 * Redis List 右侧添加
 */
public Long rightPush(
        String key,
        Object value
){

    return redisTemplate
            .opsForList()
            .rightPush(
                    key,
                    value
            );
}


/**
 * Redis List 左侧取出
 */
public Object leftPop(
        String key
){

    return redisTemplate
            .opsForList()
            .leftPop(key);

}


/**
 * Redis List 右侧取出
 */
public Object rightPop(
        String key
){

    return redisTemplate
            .opsForList()
            .rightPop(key);

}


/**
 * 获取List长度
 */
public Long size(
        String key
){

    return redisTemplate
            .opsForList()
            .size(key);

}


}
