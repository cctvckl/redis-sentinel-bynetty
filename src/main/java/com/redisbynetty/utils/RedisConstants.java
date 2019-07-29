package com.redisbynetty.utils;

import com.redisbynetty.netty.client.NettyClient;
import io.netty.util.AttributeKey;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/12 0012
 * creat_time: 17:47
 **/
public class RedisConstants {

    public static final String OK = "OK";

    public static final String PING = "PING";

    public static final String PONG = "PONG";


//    public static final String masterIp = "127.0.0.1";

    /**
     * 下面这几台都是我本地的sentinel集群中监控的redis 3节点，一主两从。
     */
    public static final String masterIp = "192.168.17.128";

    public static final Integer masterPort = 6379;

    public static final String slave1Ip = "192.168.17.129";

    public static final Integer slave1Port = 6379;

    public static final String slave2Ip = "192.168.17.130";

    public static final Integer slave2Port = 6379;


    public static final AttributeKey<NettyClient> NETTY_CLIENT_ATTRIBUTE_KEY = AttributeKey.valueOf("ChannelRelatedNettyClient");


}
