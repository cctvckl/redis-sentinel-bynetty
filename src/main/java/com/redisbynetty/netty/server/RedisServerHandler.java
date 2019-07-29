package com.redisbynetty.netty.server;

import com.redisbynetty.commands.BaseCommand;
import com.redisbynetty.commands.CommandProcessor;
import com.redisbynetty.commands.CommandRegistry;
import com.redisbynetty.utils.RedisUtils;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.redis.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * desc:
 * @author: caokunliang
 * creat_date: 2019/7/29 0029
 * creat_time: 12:55
 **/
@Slf4j
public class RedisServerHandler extends ChannelDuplexHandler {




    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        RedisMessage redisMessage = (RedisMessage) msg;

        processRedisMsg(ctx,redisMessage);
        // 是否资源
        ReferenceCountUtil.release(redisMessage);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.print("exceptionCaught: ");
        cause.printStackTrace(System.err);
        ctx.close();
    }


    private static void processRedisMsg(ChannelHandlerContext ctx, RedisMessage msg) {
        if (msg instanceof SimpleStringRedisMessage) {
            System.out.println(((SimpleStringRedisMessage) msg).content());
        } else if (msg instanceof ErrorRedisMessage) {
            System.out.println(((ErrorRedisMessage) msg).content());
        } else if (msg instanceof IntegerRedisMessage) {
            System.out.println(((IntegerRedisMessage) msg).value());
        } else if (msg instanceof FullBulkStringRedisMessage) {
            BaseCommand command = RedisUtils.getCmdFromMsg(msg);
            CommandProcessor processor = CommandRegistry.getProcessorByCmd(command);
            processor.process(ctx,command);
        } else if (msg instanceof ArrayRedisMessage) {
            BaseCommand command = RedisUtils.getCmdFromMsg(msg);
            CommandProcessor processor = CommandRegistry.getProcessorByCmd(command);
            processor.process(ctx,command);
        } else {
            throw new CodecException("unknown message type: " + msg);
        }
    }




}
