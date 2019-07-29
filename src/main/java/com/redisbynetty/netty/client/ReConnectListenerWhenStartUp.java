package com.redisbynetty.netty.client;

import com.redisbynetty.utils.RedisConstants;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/14 0014
 * creat_time: 11:33
 **/
@Slf4j
public class ReConnectListenerWhenStartUp implements GenericFutureListener<ChannelFuture> {

    private NettyClient nettyClient;


    public ReConnectListenerWhenStartUp(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            future.channel().eventLoop().schedule(new Runnable() {
                @Override
                public void run() {
                    nettyClient.connect();
                }
            },2, TimeUnit.SECONDS);

            log.error("connect failed.");
        }else {
            log.info("reconnect success");

            future.channel().attr(RedisConstants.NETTY_CLIENT_ATTRIBUTE_KEY).set(nettyClient);
        }
    }
}
