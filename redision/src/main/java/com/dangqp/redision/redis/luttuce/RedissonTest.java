package com.dangqp.redision.redis.luttuce;
 
import com.dangqp.redision.redis.luttuce.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RKeys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

import java.util.concurrent.TimeUnit;

public class RedissonTest {
 
    @Test
    public void test() throws InterruptedException {
        //redisson配置
        Config config = new Config();
        SingleServerConfig singleSerververConfig = config.useSingleServer();
        singleSerververConfig.setAddress("127.0.0.1:6379");
        //singleSerververConfig.setPassword("redis");
        StringCodec stringCodec = new StringCodec();
        config.setCodec(stringCodec);
        //redisson客户端
        RedissonClient redissonClient = RedisUtils.getInstance().getRedisson(config);
        RKeys keys = redissonClient.getKeys();
        long dang = keys.countExists("dang");
        System.out.println("是否存在dang"+dang);
        RBucket<Object> dang1 = redissonClient.getBucket("dang");
        RBucket<Object> rBucket = RedisUtils.getInstance().getRBucket(redissonClient, "dang");
        //rBucket.set("wangnian");
        System.out.println(rBucket.get());
        int i = 0;
        while (true) {
            i++;
            RLock lock = redissonClient.getLock("lock");
            lock.tryLock(0, 1, TimeUnit.SECONDS);//第一个参数代表等待时间，第二是代表超过时间释放锁，第三个代表设置的时间制
            try {
                rBucket.getAndSet("qi"+i);
                System.out.println("执行");
            } finally {
                if (i==10){
                    break;
                }
                lock.unlock();
            }
        }
    }

    @Test
    public void test1() throws InterruptedException {
        //redisson配置
        Config config = new Config();
        SingleServerConfig singleSerververConfig = config.useSingleServer();
        singleSerververConfig.setAddress("127.0.0.1:6379");
        //singleSerververConfig.setPassword("redis");
        StringCodec stringCodec = new StringCodec();
        config.setCodec(stringCodec);
        //redisson客户端
        RedissonClient redissonClient = RedisUtils.getInstance().getRedisson(config);
        RKeys keys = redissonClient.getKeys();
        long dang = keys.countExists("dang");
        System.out.println("是否存在dang"+dang);
        RBucket<Object> dang1 = redissonClient.getBucket("dang");
        RBucket<Object> rBucket = RedisUtils.getInstance().getRBucket(redissonClient, "dang");
        //rBucket.set("wangnian");
        System.out.println(rBucket.get());
        int i = 0;
        while (true) {
            i++;
            RLock lock = redissonClient.getLock("lock");
            lock.tryLock(0, 1, TimeUnit.SECONDS);//第一个参数代表等待时间，第二是代表超过时间释放锁，第三个代表设置的时间制
            try {
                rBucket.getAndSet("qi"+i);
                System.out.println("执行");
            } finally {
                if (i==10){
                    break;
                }
                lock.unlock();
            }
        }
    }
}
