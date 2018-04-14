package org.fuqinqin.code.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisClient<K, V> {

    @Autowired
    private RedisTemplate<K, V> redisTemplate;

    /**
     * 增加
     * */
    public boolean set(final K k, final V v){
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                ValueOperations<K, V> operations = redisTemplate.opsForValue();
                operations.set(k, v);
                return false;
            }
        }, false, true);

        return result;
    }

    /**
     * 获取
     * */
    public V get(final K k){
        V v = redisTemplate.execute(new RedisCallback<V>() {
            @Override
            public V doInRedis(RedisConnection redisConnection) throws DataAccessException {
                ValueOperations<K, V> operations = redisTemplate.opsForValue();
                V v = operations.get(k);
                return v;
            }
        }, false, true);
        return v;
    }

}
