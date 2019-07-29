package com.redisbynetty.netty.client;

import lombok.Data;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/14 0014
 * creat_time: 17:01
 **/
@Data
public class ChannelRelatedClientInfoVO {
    private NettyClient nettyClient;

    private Boolean isMaster;
}
