package com.inspur.core.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    //序列化数据
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
       RedisTemplate<String,Object> template =  new RedisTemplate<>();
       RedisSerializer<String> serializer =  new StringRedisSerializer();
       Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
       ObjectMapper mapper = new ObjectMapper();
       mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
       mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
       jsonRedisSerializer.setObjectMapper(mapper);
       //配置Redis工厂
       template.setConnectionFactory(factory);
        //ke序列化方式
       template.setKeySerializer(serializer);
        //value序列化
       template.setValueSerializer(jsonRedisSerializer);
       //value hashmap序列化
       template.setHashValueSerializer(jsonRedisSerializer);
       return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
        RedisSerializer<String> serializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        //解决查询缓存转换异常的问题
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jsonRedisSerializer.setObjectMapper(mapper);
        // 配置序列化（解决乱码的问题）,过期时间600秒
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(600))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer))
                .disableCachingNullValues();
        RedisCacheManager manager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
        return manager;
    }
}
