package com.redisbynetty.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/14 0014
 * creat_time: 9:55
 **/
@Slf4j
public class MonitorMasterReadTimeOutHandler extends ReadTimeoutHandler{
    public MonitorMasterReadTimeOutHandler(int timeoutSeconds) {
        super(timeoutSeconds);
    }

    public MonitorMasterReadTimeOutHandler(long timeout, TimeUnit unit) {
        super(timeout, unit);
    }

    @Override
    protected void readTimedOut(ChannelHandlerContext ctx) throws Exception {
//        super.readTimedOut(ctx);
        log.info("timeout ...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("{},MonitorMasterReadTimeOutHandler  read start",ctx.channel());
        super.channelRead(ctx, msg);
    }
}
