package com.redisbynetty.commands;

import io.netty.channel.ChannelHandlerContext;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/12 0012
 * creat_time: 19:57
 **/
public interface CommandProcessor {

    public void process(ChannelHandlerContext ctx,BaseCommand baseCommand);
}
