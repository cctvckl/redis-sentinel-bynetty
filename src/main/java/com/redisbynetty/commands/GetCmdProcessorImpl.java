package com.redisbynetty.commands;

import com.redisbynetty.storage.RedisMemoryStorage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ErrorRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.SimpleStringRedisMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/12 0012
 * creat_time: 19:57
 **/
@Slf4j
public class GetCmdProcessorImpl implements CommandProcessor {

    @Override
    public void process(ChannelHandlerContext ctx,BaseCommand baseCommand) {
        List<String> params = baseCommand.getParams();
        log.info("params:{}",params);
        if (params.size() != 1){
            // 异常返回
            ErrorRedisMessage message = new ErrorRedisMessage("wrong number of arguments for 'get' command");
            ctx.writeAndFlush(message);
            return;
        }

        String s = RedisMemoryStorage.getValue(params.get(0));
        if (s == null){
            ctx.writeAndFlush(FullBulkStringRedisMessage.NULL_INSTANCE);
            return;
        }
        SimpleStringRedisMessage message = new SimpleStringRedisMessage(s);
        ctx.writeAndFlush(message);
    }

}
