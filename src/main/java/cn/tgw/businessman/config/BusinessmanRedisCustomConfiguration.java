package cn.tgw.businessman.config;

import cn.tgw.businessman.model.Businessman;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/*
 * @Project:tgw
 * @Description:redis custom configuration
 * @Author:TjSanshao
 * @Create:2018-12-03 09:11
 *
 **/

@Configuration
public class BusinessmanRedisCustomConfiguration {

    @Bean
    public RedisCacheManager businessmanCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(30L))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<Businessman>(Businessman.class)));
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(configuration).build();
    }

}
