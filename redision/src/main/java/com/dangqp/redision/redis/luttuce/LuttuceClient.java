package com.dangqp.redision.redis.luttuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.concurrent.ExecutionException;

/**
 * Title:com.dangqp.redision.redis.luttuce
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京思特奇信息技术股份有限公司
 *
 * @author dangqp
 * @version 1.0
 * @created 2019/02/27  11:08
 */
public class LuttuceClient {

    private RedisClient redisClient;

    /**
     * 初始化
     * @param url
     * @param port
     * @return
     */
    public RedisClient init(String url, int port) {

        if (redisClient != null) {
            return redisClient;
        }
        RedisURI redisURI = RedisURI.create(url, port);
        return RedisClient.create(redisURI);
    }

    public void close() {
        if (redisClient != null) {
            redisClient.shutdown();
        }
    }

    public static void main(String[] args) {
        LuttuceClient luttuceClient = new LuttuceClient();
        RedisClient localhost = luttuceClient.init("localhost", 6379);
        //建立连接
        StatefulRedisConnection<String, String> connect = localhost.connect();
        boolean open = connect.isOpen();
        System.out.println(open);
        //同步方式
        RedisCommands<String, String> sync = connect.sync();
        //异步方式
        RedisAsyncCommands<String, String> async = connect.async();
        RedisFuture<String> dang = async.get("dang");
        try {
            //RedisFuture的get方法是阻塞方法，会一直阻塞到返回结果，可以添加超时时间
            String name = dang.get();
            System.out.println(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
