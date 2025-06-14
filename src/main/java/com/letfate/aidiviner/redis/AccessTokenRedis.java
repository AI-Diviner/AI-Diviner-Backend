package com.letfate.aidiviner.redis;

import java.util.concurrent.TimeUnit;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import com.letfate.aidiviner.entity.Special;

@Component
public class AccessTokenRedis {
    @Autowired
    @Qualifier("accessTokenRedisBean")
    private RedisTemplate<String, Integer> template;

    public void set(int id, String token) {
        this.template.opsForValue().set(id + ":" + token, id, Special.RedisSurvivalTime.accessToken, TimeUnit.DAYS);
    }

    private String getKey(String token) {
        Set<String> keys = this.template.keys("*:" + token);
        if (keys.isEmpty()) {
            return null;
        }
        return keys.iterator().next();
    }

    public Integer get(String token) {
        String key = this.getKey(token);
        if (key == null) {
            return null;
        }
        return this.template.opsForValue().get(key);
    }

    public boolean delete(String token) {
        String key = this.getKey(token);
        if (key == null) {
            return false;
        }
        return this.template.delete(key);
    }

    public void delete(int id) {
        Set<String> keys = this.template.keys(id + ":*");
        if (keys.isEmpty()) {
            return;
        }
        this.template.delete(keys);
    }
}