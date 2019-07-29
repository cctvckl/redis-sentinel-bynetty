package com.redisbynetty.netty.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;


public class RedisSentinelServerChannelInitializer extends ChannelInitializer<SocketChannel> {




	@Override
	public void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline pipeline = channel.pipeline();
        // in bound
        pipeline.addLast(new RedisDecoder());
        // in bound
        pipeline.addLast(new RedisBulkStringAggregator());
        // in bound
        pipeline.addLast(new RedisArrayAggregator());
        // out bound
        pipeline.addLast(new RedisEncoder());
        // 入栈 加 出栈
        pipeline.addLast(new RedisServerHandler());

	}


}
