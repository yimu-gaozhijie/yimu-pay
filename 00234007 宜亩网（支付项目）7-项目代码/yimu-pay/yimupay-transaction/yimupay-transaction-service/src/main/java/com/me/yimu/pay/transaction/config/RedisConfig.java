package com.me.yimu.pay.transaction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.me.yimu.pay.cache.Cache;
import com.me.yimu.pay.transaction.common.util.RedisCache;

/**
 * redis配置类
 * @author PC
 *
 */
@Configuration
public class RedisConfig {

    @Bean
    public Cache cache(StringRedisTemplate redisTemplate){
        return new RedisCache(redisTemplate);
    }
    
}
