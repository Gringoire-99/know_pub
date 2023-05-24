package com.gg.kp_common.config;

import com.gg.kp_common.entity.po.UserDetail;
import com.gg.kp_common.utils.JacksonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, UserDetail> createRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, UserDetail> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        JacksonRedisSerializer<Object> serializer = new JacksonRedisSerializer<>(Object.class);


        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();

        return template;
    }

}