package com.dangqp.redision.redis.redisssion;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;

/**
 * Title:com.dangqp.redision.redis.redisssion
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京思特奇信息技术股份有限公司
 *
 * @author dangqp
 * @version 1.0
 * @created 2019/02/27  13:03
 */
public class RedssionClient {
    public static void main(String[] args) throws IOException {
        //程序化配置方法
        Config config = new Config();
        config.useSingleServer().setAddress("127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        boolean shutdown = redisson.isShutdown();
        System.out.println(shutdown);
        Config config1 = redisson.getConfig();
        System.out.println(config1.toJSON().toString());
        RList<Object> list = redisson.getList("list");
        for (Object o : list) {
            System.out.println(o);
        }
        System.out.println("-------------------------------");
        RAtomicLong myLong = redisson.getAtomicLong("myLong");
//        long addAndGet = myLong.addAndGet(10);
//        System.out.println(addAndGet);
        boolean compareAndSet = myLong.compareAndSet(22, 20);
        System.out.println(compareAndSet);
        boolean compareAndSet1 = myLong.compareAndSet(10, 20);
        System.out.println(compareAndSet1);
        RLock redissonLock = redisson.getLock("RedLock");
    }
}
