package com.demo.backend.common.redis;

public class RedisKey {

    public static String USER(long id){
        return "user:" + id;
    }
    
}
