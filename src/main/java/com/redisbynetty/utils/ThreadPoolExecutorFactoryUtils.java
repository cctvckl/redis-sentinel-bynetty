package com.redisbynetty.utils;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * desc:
 *
 * @author : caokunliang
 * creat_date: 2019/2/19 0019
 * creat_time: 14:12
 **/
public class ThreadPoolExecutorFactoryUtils {

    private static final NamedThreadFactory namedThreadFactoryForMonitorMaster = new NamedThreadFactory("MONITOR_MASTER");


    private static final NamedThreadFactory namedThreadFactoryForMdvrResourceVehicleAlarm = new NamedThreadFactory("MDVR_RESOURCE_VEHICLE_ALARM");


    /**
     * 无界队列，保证任务不被拒绝
     */
    public static final ScheduledThreadPoolExecutor THREAD_POOL_EXECUTOR_MONITOR_MASTER = new ScheduledThreadPoolExecutor(1,namedThreadFactoryForMonitorMaster);

    /**
     * 无界队列，保证任务不被拒绝;
     */
    public static ThreadPoolExecutor THREAD_POOL_EXECUTOR_VEHICLE_RELATED_RMS;

    /**
     * 无界队列，保证任务不被拒绝
     */
    public static final ThreadPoolExecutor THREAD_POOL_EXECUTOR_MDVR_RESOURCE_VEHICLE_ALARM = new ThreadPoolExecutor(2,
            2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactoryForMdvrResourceVehicleAlarm);


}
