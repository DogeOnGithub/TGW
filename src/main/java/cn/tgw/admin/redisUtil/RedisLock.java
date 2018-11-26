package cn.tgw.admin.redisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RedisLock {

    @Autowired
    StringRedisTemplate redisTemplate;


    /**
     * 加锁操作
     * @param key  商品id
     * @param value  当前时间+过期时间，就是拥有锁的时间作为key
     * @return
     */
    public boolean lock(String key, String value){
        /**
         * 单线程操作，只有一个线程能设置key value 成功，成功则返回true，加锁成功；
         */
        if(redisTemplate.opsForValue().setIfAbsent(key, value)){
            return true;
        }
        //解决锁过期问题
        String currentValue=redisTemplate.opsForValue().get(key);
        //如果锁过期了
        if (!StringUtils.isEmpty(currentValue)&&Long.parseLong(currentValue)<System.currentTimeMillis()){
            //获取上一个锁时间,并且设置当前锁的时间，返回的是上个锁的value值
            String oldValue=redisTemplate.opsForValue().getAndSet(key, value);

            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }
        return  false;
    }

    /**
     * 解锁操作
     * @param key
     * @param value
     */
    public void unlock(String key,String value){

        String cerrentValue=redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(cerrentValue)&&cerrentValue.equals(value)){
            redisTemplate.opsForValue().getOperations().delete(key);
        }

    }




}
