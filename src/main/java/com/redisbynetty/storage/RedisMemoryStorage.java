package com.redisbynetty.storage;

import java.util.HashMap;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/7/12 0012
 * creat_time: 20:17
 **/
public class RedisMemoryStorage {

    private static HashMap<String,String> map = new HashMap<>();


    public static String put(String key, String value){
        return map.put(key,value);
    }

    public static String getValue(String key){
        return map.get(key);
    }

}
