package com.example.pekon.javatest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * create by dsw on 2020-10-21
 **/
public class ThreadPoolUtils {
    //CPU核心数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    //最大线程数
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    //非核心线程闲置的超时时间
    private static final int KEEP_ALIVE_TIME = 1;
    //任务队列
    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);
    //线程池
    private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, sPoolWorkQueue);
}
