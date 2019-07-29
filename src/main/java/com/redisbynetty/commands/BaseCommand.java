package com.redisbynetty.commands;

import lombok.Data;

import java.util.List;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/12 0012
 * creat_time: 19:45
 **/
@Data
public class BaseCommand {
    /**
     * 命令名
     */
    private String commandName;

    /**
     * 参数
     */
    private List<String> params;
}
