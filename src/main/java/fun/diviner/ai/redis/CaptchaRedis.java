package fun.diviner.ai.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import fun.diviner.ai.entity.Special;

@Component
public class CaptchaRedis {
    @Autowired
    @Qualifier("captchaRedisBean")
    private RedisTemplate<String, String> template;

    public void set(String key, String code) {
        this.template.opsForValue().set(key, code, Special.RedisSurvivalTime.captcha, TimeUnit.MINUTES);
    }

    public String get(String key) {
        return this.template.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return this.template.delete(key);
    }
}