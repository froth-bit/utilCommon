package com.wyc.utils.common.redisManage;

public class RedisUtils {

    String bigKeys = "redis-cli -h 127.0.0.1 -p6379 -a \"password\" --bigkeys";
    String RdbTools = "rdb dump.rdb -c memory --bytes 10240 -f redis.csv";

}
