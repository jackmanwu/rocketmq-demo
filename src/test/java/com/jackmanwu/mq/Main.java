package com.jackmanwu.mq;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jackmanwu
 * @create 2019-09-26 09:13:36
 * @description
 */
@Slf4j
public class Main {
    private ConcurrentHashMap<String, AtomicLong> counterMap = new ConcurrentHashMap<>();

    public Long incr(String counterName, Long delta) {
        synchronized (counterName.intern()) {
            counterMap.putIfAbsent(counterName, new AtomicLong(0));
            Long num = counterMap.get(counterName).addAndGet(delta);
            log.info("计数一次后的值：{}", num);
            return num;
        }
    }

    public Long getAndClear(String counterName) {
        synchronized (counterName.intern()) {
            counterMap.putIfAbsent(counterName, new AtomicLong(0));
            Long num = counterMap.get(counterName).getAndSet(0);
            log.info("清0之前的值：{}", num);
            return num;
        }
    }

    public static void main(String[] args) {
        String p1 = "order";
        String p2 = "product";
        Main main = new Main();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(110);
        ExecutorService executorService = Executors.newFixedThreadPool(110);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Thread(new Incr(p1, main, cyclicBarrier), "线程" + i));
        }
        for (int i = 0; i < 10; i++) {
            executorService.execute(new CleatCounter(p1,main,cyclicBarrier));
        }
        executorService.shutdown();
    }

    static class Incr implements Runnable {
        private String projectName;

        private Main main;

        private CyclicBarrier cyclicBarrier;


        public Incr(String projectName, Main main, CyclicBarrier cyclicBarrier) {
            this.projectName = projectName;
            this.main = main;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            /*try {
                log.info("等待其他线程");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }*/
            main.incr(projectName, 1L);
        }
    }

    static class CleatCounter implements Runnable {
        private String projectName;

        private Main main;

        private CyclicBarrier cyclicBarrier;

        public CleatCounter(String projectName, Main main, CyclicBarrier cyclicBarrier) {
            this.projectName = projectName;
            this.main = main;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            /*try {
                log.info("等待其他线程111");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }*/
            main.getAndClear(projectName);
        }
    }
}
