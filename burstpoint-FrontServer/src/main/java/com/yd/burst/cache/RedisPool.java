package com.yd.burst.cache;

import com.yd.burst.util.SerializeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;

/**
 *
 * Redis连接池工具类
 * @author Will
 */
public class RedisPool implements RedisUtil, Serializable {

    private static Logger logger = LogManager.getLogger(RedisPool.class);

    @Autowired
    private JedisPool jedisPool;


    @Override
    public void set(String key, String value) {
        Jedis jedis = this.getJedis();
        jedis.set(key, value);
        this.releaseJedis(jedis);
    }

    @Override
    public void setData4Object2Redis(String key, Object object) {
        //序列化
        byte[] bytes = SerializeUtil.serialize(object);

        //存入redis
        Jedis jedis = this.getJedis();
        jedis.set(key.getBytes(), bytes);
        this.releaseJedis(jedis);
    }

    @Override
    public Object getData4Object2Redis(String key) {
        //查询
        Jedis jedis = null;
        try{
            jedis = this.getJedis();
            byte[] result = jedis.get(key.getBytes());
            if(null == result){
                return null;
            }
            return SerializeUtil.unSerialize(result);
        } catch (Exception e) {
            logger.error("获取缓存出错{0}", key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public void set(String key, String value, Integer seconds) {
        Jedis jedis = this.getJedis();
        jedis.set(key, value);
        jedis.expire(key, seconds);
        this.releaseJedis(jedis);
    }

    @Override
    public String get(String key) {
        Jedis jedis = this.getJedis();
        String result = jedis.get(key);
        this.releaseJedis(jedis);
        return result;
    }

    @Override
    public void del(String key) {
        Jedis jedis = this.getJedis();
        jedis.del(key);
        this.releaseJedis(jedis);
    }

    @Override
    public void expire(String key, Integer seconds) {
        Jedis jedis = this.getJedis();
        jedis.expire(key, seconds);
        this.releaseJedis(jedis);
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = this.getJedis();
        Long count = jedis.incr(key);
        this.releaseJedis(jedis);
        return count;
    }

    /**
     * 获取Jedis连接
     *
     * @return Jedis连接
     */
    private Jedis getJedis() {
        return this.jedisPool.getResource();
    }

    /**
     * 释放Jedis连接,返还到连接池
     *
     * @param jedis
     *            jedis连接
     */
    private void releaseJedis(Jedis jedis) {
        jedis.close();
    }
}