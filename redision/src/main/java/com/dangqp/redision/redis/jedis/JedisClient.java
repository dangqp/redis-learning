package com.dangqp.redision.redis.jedis;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Title:com.dangqp.redision.redis.jedis
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京思特奇信息技术股份有限公司
 *
 * @author dangqp
 * @version 1.0
 * @created 2019/02/27  10:56
 */
public class JedisClient {

    private Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池

    public JedisClient() {
        initialPool();
        initialShardedPool();
        shardedJedis = shardedJedisPool.getResource();
        jedis = jedisPool.getResource();


    }

    /**
     * 初始化非切片池
     */
    private void initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000L);
        config.setTestOnBorrow(false);
        jedisPool = new JedisPool(config, "127.0.0.1", 6379);
    }

    /**
     * 初始化切片池
     */
    private void initialShardedPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000l);
        config.setTestOnBorrow(false);
        // slave链接
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo("127.0.0.1", 6379, "master"));

        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    public void close() {
        if (jedis != null) {
            Jedis resource = jedisPool.getResource();
            jedis.close();
        }
    }

    public void show() {
        jedisPool.returnResource(jedis);
        shardedJedisPool.returnResource(shardedJedis);
    }

    public static void main(String[] args) {
        JedisClient jedisClient = new JedisClient();
        Jedis jedis = jedisClient.jedis;
        String ping = jedis.ping();
        System.out.println(ping);
        System.out.println(jedis.get("2"));
        List<String> list = jedis.lrange("list", 0, -1);
        System.out.println(list);
    }
}
