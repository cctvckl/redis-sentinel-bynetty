package com.redisbynetty.netty.client;

import com.redisbynetty.utils.RedisConstants;
import io.netty.bootstrap.Bootstrap;

/**
 * desc:
 *
 * @author: caokunliang
 * creat_date: 2019/1/10 0010
 * creat_time: 10:18
 **/
public class RedisClientBootStrap {


    public static void version1() {
        Bootstrap bootstrap = ClientBootStrapFactory.build();

        {
            NettyClient singleBootstrapVersion =
                    new NettyClient(RedisConstants.masterIp, RedisConstants.masterPort, bootstrap, true);
            singleBootstrapVersion.setGenericFutureListener(new ReConnectListenerWhenStartUp(singleBootstrapVersion));
            singleBootstrapVersion.connect();
        }

        {
            NettyClient singleBootstrapVersion =
                    new NettyClient(RedisConstants.slave1Ip, RedisConstants.slave1Port, bootstrap, false);
            singleBootstrapVersion.setGenericFutureListener(new ReConnectListenerWhenStartUp(singleBootstrapVersion));
            singleBootstrapVersion.connect();
        }

        {
            NettyClient singleBootstrapVersion =
                    new NettyClient(RedisConstants.slave2Ip, RedisConstants.slave2Port, bootstrap, false);
            singleBootstrapVersion.setGenericFutureListener(new ReConnectListenerWhenStartUp(singleBootstrapVersion));
            singleBootstrapVersion.connect();
        }

    }

    public static void main(String[] args) {
        version1();
    }


}
