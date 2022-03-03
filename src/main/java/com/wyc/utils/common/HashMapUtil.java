package com.wyc.utils.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapUtil {

    public HashMap<Object,Object> hashMap;

    Map<String,Object> concurrentHashMap= new ConcurrentHashMap<>();

    public static HashMapUtil createHashMap(){
        HashMapUtil listUtil = new HashMapUtil();
        listUtil.hashMap = new HashMap<>();
        return listUtil;
    }

    public static HashMapUtil createLinkedHashMap(){
        HashMapUtil listUtil = new HashMapUtil();
        listUtil.hashMap = new LinkedHashMap<>();
        return listUtil;
    }

    public HashMapUtil put(Object key, Object value){
        hashMap.put(key,value);
        return this;
    }
    public HashMapUtil putAll(Map<Object,Object> map){
        hashMap.putAll(map);
        return this;
    }
    public HashMapUtil put(String[][] arr){
        for (String[] strings : arr) {
            hashMap.put(strings[0], strings[1]);
        }
        return this;
    }

    public HashMap<Object,Object> get(){
        return hashMap;
    }

}
