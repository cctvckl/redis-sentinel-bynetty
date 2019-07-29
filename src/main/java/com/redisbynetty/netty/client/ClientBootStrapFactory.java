package com.redisbynetty.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/14 0014
 * creat_time: 15:40
 **/
public class ClientBootStrapFactory {

    public static Bootstrap build() {
        Bootstrap bootstrap;
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new MonitorMasterReadTimeOutHandler(11));
                pipeline.addLast(new RedisDecoder());
                pipeline.addLast(new RedisBulkStringAggregator());
                pipeline.addLast(new RedisArrayAggregator());
                pipeline.addLast(new RedisEncoder());
                pipeline.addLast(new RedisClientHandler());
            }
        });

        return bootstrap;
    }
}
