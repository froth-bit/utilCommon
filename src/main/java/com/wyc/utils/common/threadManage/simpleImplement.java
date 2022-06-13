package com.wyc.utils.common.threadManage;

public class simpleImplement {

    public Thread createThread(){
        return new Thread(()-> {
        });
    }

    public Thread createThread(String name){
        return new Thread(()->{
        },name);
    }




}
