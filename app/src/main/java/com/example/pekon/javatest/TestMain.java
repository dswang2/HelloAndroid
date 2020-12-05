package com.example.pekon.javatest;

import android.os.SystemClock;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * create by dsw on 2020-10-17
 **/
public class TestMain {
    public static TestMain testMain;
    public Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        testMain = new TestMain();
         threadDemo1();
        threadDemo2();
    }

    private static void threadDemo2() {
        testMain.index = 0;
        // 线程池 1
        ExecutorService executorService = new ThreadPoolExecutor(3, 3,
                1, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        for (int c = 0; c < 10; c++) {
            final int finalC = c;
            // 往线程池 1 提交任务
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        System.out.println("" + Thread.currentThread().getName() + ",c="+ finalC +", i=" + i + " -- " + testMain.index);
                        if(finalC%2==0){
                            try {
                                wait(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if(finalC==9){
                            try {
                                TimeUnit.MICROSECONDS.sleep(3);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        testMain.indexIncrease();
                        System.out.println("" + Thread.currentThread().getName() + ",c="+ finalC +" i=" + i + " -- " + testMain.index);
                    }
                }
            });
        }
        // 线程池 2
//        ExecutorService executorService2 = Executors.newFixedThreadPool(3);
        ExecutorService executorService2 = new ThreadPoolExecutor(3, 3,
                1, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        for (int c = 0; c < 10; c++) {
            final int finalC = c;
            // 往线程池 2 提交任务
            executorService2.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100; i++) {
                        System.out.println("" + Thread.currentThread().getName() + ",c2="+ finalC +", i=" + i + " -- " + testMain.index);
                        testMain.indexIncrease();
                        System.out.println("" + Thread.currentThread().getName() + ",c2="+ finalC +" i=" + i + " -- " + testMain.index);
                    }
                }
            });
        }
        // Thread.activeCount() // 只是一个估计数量，并不准确
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(testMain.index);
    }


    public int index = 0;

    private static void threadDemo1() {
        System.out.println("hello as java");
        //
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 1000; i++) {
                    System.out.println("" + Thread.currentThread().getName() + ",i=" + i + " -- " + testMain.index);
                    testMain.indexIncrease();
                    System.out.println("" + Thread.currentThread().getName() + ",i=" + i + " -- " + testMain.index);

                }
            }
        }.start();
        //
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("" + Thread.currentThread().getName() + ",i=" + i + " -- " + testMain.index);
                    testMain.indexIncrease();
                    System.out.println("" + Thread.currentThread().getName() + ",i=" + i + " -- " + testMain.index);
                }
            }
        };
        new Thread(runnable).start();
        //
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("" + Thread.currentThread().getName() + ",i=" + i + " -- " + testMain.index);
                    testMain.indexIncrease();
                    System.out.println("" + Thread.currentThread().getName() + ",i=" + i + " -- " + testMain.index);
                }
            }
        }).start();
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(testMain.index);
    }

    private static void sleep() {
        try {
            TimeUnit.MICROSECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void indexIncrease() {
        TestMain.sleep();
        index++;
    }
}
