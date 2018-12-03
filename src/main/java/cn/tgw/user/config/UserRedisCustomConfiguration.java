package cn.tgw.user.config;

import cn.tgw.user.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;
import java.time.Duration;

/*
 * @Project:tgw
 * @Description:redis custom configuration
 * @Author:TjSanshao
 * @Create:2018-12-03 09:09
 *
 **/

@Configuration
public class UserRedisCustomConfiguration {

    //注入自定义RedisCacheManager，设置键值的序列化器
    @Bean
    @Primary
    public RedisCacheManager userCacheManager(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(30L))//设置该缓存管理的键过期时间
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<User>(User.class)));
        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(configuration).build();
    }

}
