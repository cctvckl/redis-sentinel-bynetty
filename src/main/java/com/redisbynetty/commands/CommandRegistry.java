package com.redisbynetty.commands;

import java.util.concurrent.ConcurrentHashMap;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/12 0012
 * creat_time: 19:58
 **/
public class CommandRegistry {
    private static ConcurrentHashMap<String,CommandProcessor> cmdMap = new ConcurrentHashMap<>();


    static {
        init();
    }

    public static void init(){
        cmdMap.put("COMMAND".toUpperCase(),new CommandsCmdProcessorImpl());
        cmdMap.put("SET".toUpperCase(),new SetCmdProcessorImpl());
        cmdMap.put("get".toUpperCase(),new GetCmdProcessorImpl());
    }

    public static CommandProcessor getProcessorByCmd(BaseCommand baseCommand){
        CommandProcessor processor = cmdMap.get(baseCommand.getCommandName().toUpperCase());
        return processor;
    }
}
