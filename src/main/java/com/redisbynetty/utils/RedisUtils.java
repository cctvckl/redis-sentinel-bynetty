package com.redisbynetty.utils;

import com.redisbynetty.commands.BaseCommand;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/12 0012
 * creat_time: 19:46
 **/
@Slf4j
public class RedisUtils {


    public static BaseCommand convert2Command(List<String> rawStrArray) {
        BaseCommand command = new BaseCommand();
        command.setCommandName(rawStrArray.get(0));
        command.setParams(rawStrArray.subList(1, rawStrArray.size()));

        return command;
    }

    public static BaseCommand getCmdFromMsg(RedisMessage msg) {
        if (msg instanceof ArrayRedisMessage) {
            List<String> list = getStringsFromArray(((ArrayRedisMessage) msg));
            BaseCommand command = convert2Command(list);
            log.info("receive command:{}", command);

            return command;
        } else {
            log.error("unsupported command.");
        }

        return null;
    }

    public static String getString(FullBulkStringRedisMessage msg) {
        if (msg.isNull()) {
            return "(null)";
        }
        return msg.content().toString(CharsetUtil.UTF_8);
    }

    public static List<String> getStringsFromArray(ArrayRedisMessage arrayRedisMessage) {
        ArrayList<String> list = new ArrayList<>();
        if (arrayRedisMessage == null) {
            return list;
        }

        List<RedisMessage> children = arrayRedisMessage.children();
        for (RedisMessage child : children) {
            if (child instanceof FullBulkStringRedisMessage) {
                FullBulkStringRedisMessage message = (FullBulkStringRedisMessage) child;
                String s = message.content().toString(CharsetUtil.UTF_8);
                list.add(s);
            }
        }

        return list;
    }

    public static FullBulkStringRedisMessage build(ChannelHandlerContext ctx, String content) {
        return new FullBulkStringRedisMessage(ByteBufUtil.writeUtf8(ctx.alloc(), content));
    }

    public static ArrayRedisMessage build(FullBulkStringRedisMessage... fullBulkStringRedisMessage) {
        List<RedisMessage> children = new ArrayList<>(fullBulkStringRedisMessage.length);
        children.addAll(Arrays.asList(fullBulkStringRedisMessage));

        return new ArrayRedisMessage(children);
    }
}
