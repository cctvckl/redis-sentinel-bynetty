package com.redisbynetty.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/12 0012
 * creat_time: 21:29
 **/
@Slf4j
@Data
public class NettyClient {

    private String host;

    private Integer port;

    private GenericFutureListener genericFutureListener;

    private Bootstrap bootstrap;

    private Boolean isMaster;

    public NettyClient(String host, Integer port, Bootstrap bootstrap, Boolean isMaster) {
        this.host = host;
        this.port = port;
        this.bootstrap = bootstrap;
        this.isMaster = isMaster;
    }



    public void connect() {
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port);
            channelFuture.addListener(genericFutureListener);
        } catch (Exception e) {
            log.error("{}",e);
        }
    }

}
