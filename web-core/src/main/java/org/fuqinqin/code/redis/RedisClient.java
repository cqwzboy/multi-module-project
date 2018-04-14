package org.fuqinqin.code.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisClient<K, V> {

    /**
     * redis默认持久时间(秒)
     * */
    public static final Long DEFAULT_EXPIRE = 12*60*60L;

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    /**
     * @desc 增加
     * @param k 键
     * @param v 值
     * */
    public void set(K k, V v){
        this.redisTemplate.opsForValue().set(k, v, DEFAULT_EXPIRE);
    }

    /**
     * @desc 增加
     * @param k 键
     * @param v 值
     * @param expire 过期时间（默认秒）
     * */
    public void set(K k, V v, Long expire){
        this.redisTemplate.opsForValue().set(k, v, expire, TimeUnit.SECONDS);
    }

    /**
     * @desc 批量添加
     * @param map 键值对map
     * */
    public void multiSet(Map<? extends K, ? extends V> map){
        this.redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * @desc 删除
     * @param k 建
     * */
    public void delete(K k){
        this.redisTemplate.delete(k);
    }

    /**
     * 获取
     * */
    public V get(K k){
        return redisTemplate.opsForValue().get(k);
    }

    /**
     * 获取失效时间
     * */
    public Long getExpire(K k){
        Long expire = redisTemplate.getExpire(k, TimeUnit.SECONDS);
        return expire;
    }

    /**
     * 获取value的数据类型
     * */
    public DataType type(K k){
        return this.redisTemplate.type(k);
    }

}
