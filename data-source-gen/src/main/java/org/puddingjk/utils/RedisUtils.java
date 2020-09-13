package org.puddingjk.utils;

import org.puddingjk.common.Constants;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class RedisUtils {

    private RedisTemplate redisTemplate;

    public RedisUtils(RedisTemplate redisTemplate) {
        Objects.requireNonNull(redisTemplate);
        this.redisTemplate =redisTemplate;
    }


    /***
     * 持有锁
     * @param key 商品对应的唯一的用于分布式锁的key值
     * @return
     */
    public boolean lock(String key) {
        return (boolean) redisTemplate.execute((RedisCallback) redisConnection -> {
            StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
            stringRedisSerializer.serialize(key);
            stringRedisSerializer.serialize(key);
            Boolean flag = redisConnection.setNX(key.getBytes(), "aaa".getBytes());
            if (flag) {
                redisConnection.expire(key.getBytes(), Constants.Redis_Expire.DEFAULT_EXPIRE);
            }
            return flag;

        });
    }
    //        return (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();
//                byte keyByte[]=stringRedisSerializer.serialize(key);
//                //value可以随意设置
//                byte valueByte[]=stringRedisSerializer.serialize("lock");
//                boolean flag=redisConnection.setNX(keyByte,valueByte);
//                //防止死锁，设置最大处理的超时时间
//                if(flag) redisConnection.expire(keyByte, Constants.Redis_Expire.DEFAULT_EXPIRE);
//                return flag;
//            }
//        });
    /***
     * 释放锁
     * @param key
     * @return
     */
    public void unLock(String key){
        redisTemplate.setKeySerializer(new StringRedisSerializer());//设置序列化
        redisTemplate.delete(key);
    }

}
