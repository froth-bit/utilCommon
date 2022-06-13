package com.wyc.utils.common.threadManage;

import cn.hutool.http.HttpRequest;

import java.text.NumberFormat;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * java模拟多线程高并发
 * 原文地址：https://blog.csdn.net/u010642004/article/details/50042781
 * @author Administrator
 *
 */
public class ThreadProxyApiTest {

    private static AtomicInteger atomicInteger = new AtomicInteger();
    // 总访问量是clientNum，并发量是threadNum
    int threadNum = 10;
    int clientNum = 200;

    float avgExecTime = 0;
    float sumExecTime = 0;
    long firstExecTime = Long.MAX_VALUE;
    long lastDoneTime = Long.MIN_VALUE;
    float totalExecTime = 0;

    public static void main(String[] args) {
        new ThreadProxyApiTest().run();
        System.out.println("over!");
    }

    public void run() {

        final ConcurrentHashMap<Integer, ThreadRecord> records = new ConcurrentHashMap<>();
        final ConcurrentHashMap<Integer, String> jiShu = new ConcurrentHashMap<>();

        // 创建线程池
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        // CountDownLatch模拟并发同时访问
        final CountDownLatch doneSignal = new CountDownLatch(clientNum);

        for (int i = 0; i < clientNum; i++) {
            Runnable run = () -> {
                int index = getIndex();
                long systemCurrentTimeMillis = System.currentTimeMillis();
                Random random = new Random();
                int rNum = random.nextInt(10);
                boolean b = rNum %2==0;
                try {
                    HttpRequest.put("http://localhost:13146/hfMdmApi/coFinancePeriodLockApi/testMainMethods")
                            .header("Authorization",b?"bearer OTliYTkyNTEtNWZiMi00YjJiLWEyMmItMTRkYWFhN2RhNTlk":"bearer YjY5OTg4MWItMzk5Zi00ODY5LTgwY2MtZGZhMTgzNjA2NDY4")
                            .execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (b){
                    jiShu.put(index, "hf");
                }else {
                    jiShu.put(index, "cs");
                }

                records.put(index, new ThreadRecord(systemCurrentTimeMillis, System.currentTimeMillis()));
                doneSignal.countDown();// 每调用一次countDown()方法，计数器减1
            };
            exec.execute(run);
        }

        try {
            //到并发量直接发送
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 获取每个线程的开始时间和结束时间
         */
        for (int i : records.keySet()) {
            ThreadRecord r = records.get(i);
            sumExecTime += ((double) (r.endTime - r.startTime)) / 1000;

            if (r.startTime < firstExecTime) {
                firstExecTime = r.startTime;
            }
            if (r.endTime > lastDoneTime) {
                this.lastDoneTime = r.endTime;
            }
        }

        this.avgExecTime = this.sumExecTime / records.size();
        this.totalExecTime = ((float) (this.lastDoneTime - this.firstExecTime)) / 1000;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);

        int hfNum = 0;
        int csNum = 0;
        for (Integer integer : jiShu.keySet()) {
            if (jiShu.get(integer).equals("hf")){
                hfNum++;
            }else {
                csNum++;
            }
        }

        System.out.println("======================================================");
        System.out.println("线程数量:  " + threadNum);
        System.out.println("客户端数量: " + clientNum);
        System.out.println("平均执行时间: " + nf.format(this.avgExecTime) + "秒");
        System.out.println("总执行时间: " + nf.format(this.totalExecTime) + "秒");
        System.out.println("吞吐量:  " + nf.format(this.clientNum / this.totalExecTime) + "次每秒");
        System.out.println("hf发送:  "+hfNum+";cs发送:  "+csNum);
        System.out.println(records.keySet().size());
    }

    public static int getIndex() {
        return atomicInteger.incrementAndGet();
    }

}

class ThreadRecord {
    long startTime;
    long endTime;

    public ThreadRecord(long st, long et) {
        this.startTime = st;
        this.endTime = et;
    }

}