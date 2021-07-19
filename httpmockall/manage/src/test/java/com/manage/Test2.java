package com.manage;

import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.*;

public class Test2 {
    private static ExecutorService threadPool = null;
    @Value("${olap.max.task.num:3}")
    private int maxTaskNum;
    private volatile boolean needRun;

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
    }
    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
    }
//    public static ExecutorService newSingleThreadExecutor() {
//        return new FinalizableDelegatedExecutorService
//                (new ThreadPoolExecutor(1, 1,
//                        0L, TimeUnit.MILLISECONDS,
//                        new LinkedBlockingQueue<Runnable>()));
//    }
//








}
