package com.redisbynetty.runnable;

import com.redisbynetty.utils.RedisConstants;
import com.redisbynetty.utils.RedisUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/12 0012
 * creat_time: 21:26
 **/
@Slf4j
public class MonitorMasterTask implements Runnable {
    ChannelHandlerContext ctx;

    private AtomicInteger failCount = new AtomicInteger(0);

    public MonitorMasterTask(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }


    @Override
    public void run() {
        FullBulkStringRedisMessage message = RedisUtils.build(ctx, RedisConstants.PING);
        ArrayRedisMessage arrayRedisMessage = RedisUtils.build(message);
        ctx.writeAndFlush(arrayRedisMessage);
    }
}
