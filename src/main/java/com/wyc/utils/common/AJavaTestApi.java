package com.wyc.utils.common;

import com.alibaba.fastjson.JSONObject;
import com.wyc.utils.wo.service.ScmWoHdrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/javaTestApi")
public class AJavaTestApi {

    @Autowired
    MailUtils mailUtils;
    @Autowired
    ScmWoHdrService woHdrService;

    @PutMapping("/sendMailTest")
    public Boolean sendMailTest(@RequestBody JSONObject jsonObject){
        Boolean aBoolean = mailUtils.sendAttachmentsMail("2397521545@qq.com", "测试邮件", "内容", null, jsonObject);
        return aBoolean;
    }

    @PutMapping("/testSync")
    @Transactional(rollbackFor = Exception.class)
    public Boolean testSync(HttpServletRequest request) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(Math.min(1, 100));
        CompletableFuture<Boolean> returnString = CompletableFuture.supplyAsync(() -> {
            woHdrService.removeById("2627faf5abf1d9e94f7425296bf584ec");
            System.out.println(1/0);
            return true;
        },executor).exceptionally(throwable->{
            System.out.println(throwable.getMessage());
            throw new RuntimeException(throwable.getMessage());
        });
        return returnString.get();
    }

    @GetMapping("/testThread")
    public void testThread(){


        Thread thread = new Thread(() -> {
            System.out.println("线程动作");
        },"线程名");

        /**
         * 线程池管理
         */
        //可变大小线程池，按照任务数来分配线程，
        ExecutorService e1 = Executors.newCachedThreadPool();
        //单线程池，相当于FixedThreadPool(1)
        ExecutorService e2 = Executors.newSingleThreadExecutor();
        //固定大小线程池。
        ExecutorService e3 = Executors.newFixedThreadPool(3);
        //将线程放进线程池管理
        e1.execute(thread);

        ThreadPoolTaskExecutor y = new ThreadPoolTaskExecutor();
        thread.setUncaughtExceptionHandler((t, e) -> {

        });
        thread.start();
    }

}
