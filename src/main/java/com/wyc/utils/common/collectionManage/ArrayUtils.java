package com.wyc.utils.common.collectionManage;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayUtils {

    /**
     * 字符串数组去重并返回字符串
     * @param str
     * @return
     */
    public static String checkArr(String[] str) {
        List<String> list = new ArrayList<>();
        for (String s : str) {
            if (!list.contains(s)) {
                list.add(s);
            }
        }
        return String.join(",", list);
    }

    public static String checkArr(String str,String arg) {
        return checkArr(str.split(arg));
    }

    /**
     * 通用集合removeIf方法
     * @param a
     * @param predicate
     * @param <T>
     */
    public static <T> void listRemoveIf(Collection<T> a, Predicate<T> predicate){
        a.removeIf(predicate);
    }


    /**
     * 比较实体集合
     * @param list1 集合A
     * @param list2 集合B
     * @param cmpType 比较方式 a并集 b并集后根据arg去重 c交集 d差集 e左集合A的差 f右集合B的差
     * @param arg 比较字段 字符串数组 可多字段比较
     * @param <T> 实体对象泛型
     * @return
     */
    public static <T> List<T> compareList(List<T> list1, List<T> list2, String cmpType, String... arg){
        List<T> retList = new ArrayList<>();
        List<T> listAll = new ArrayList<>();

        listAll.addAll(list1);
        listAll.addAll(list2);

        if("a".equals(cmpType)){
            //合并后的集合 并集
            retList = listAll;
        }
        if("b".equals(cmpType)){
            //合并去重后的集合 并集去重
            retList = listAll.stream().filter(
                    distinctByKey(p -> convertString(p,arg))
            ).collect(Collectors.toList());
        }
        if("c".equals(cmpType) || "d".equals(cmpType) || "e".equals(cmpType) || "f".equals(cmpType)){
            //相同的集合 交集
            List<T> listSameTemp = new ArrayList<>();
            list1.forEach(a -> list2.forEach(b->{
                if (convertString(a,arg).equals(convertString(b,arg))){
                    listSameTemp.add(a);
                }
            }));
            retList = listSameTemp.stream().filter(
                    distinctByKey(p -> convertString(p,arg))
            ).collect(Collectors.toList());

            //不同的集合 差集
            if("d".equals(cmpType)){
                List<T> listTemp = new ArrayList<>(listAll);
                Iterator<T> it=listTemp.iterator();
                while(it.hasNext()){
                    T next = it.next();
                    for (Object o : retList) {
                        if(convertString(next,arg).equals(convertString(o,arg))){
                            it.remove();
                        }
                    }
                }
                retList = listTemp;
            }
            //list1中不在list2中的集合 左新增
            if("e".equals(cmpType)){
                List<T> listTemp = new ArrayList<>(list1);
                Iterator<T> it=listTemp.iterator();
                while(it.hasNext()){
                    T next = it.next();
                    for (Object o : retList) {
                        if(convertString(next,arg).equals(convertString(o,arg))){
                            it.remove();
                        }
                    }
                }
                retList = listTemp;
            }
            //list2中不在list1中的集合 右删除
            if("f".equals(cmpType)){
                List<T> listTemp = new ArrayList<>(list2);
                Iterator<T> it=listTemp.iterator();
                while(it.hasNext()){
                    T next = it.next();
                    for (Object o : retList) {
                        if(convertString(next,arg).equals(convertString(o,arg))){
                            it.remove();
                        }
                    }
                }
                retList = listTemp;
            }
        }
        return retList;
    }

    //比较的key
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static JSONObject convertObject(Object o){
        if (ObjectUtil.isEmpty(o)) return null;
        return JSONObject.parseObject(JSONObject.toJSONString(o));
    }

    public static String convertString(Object o,String... arg){
        StringBuilder buffer = new StringBuilder();
        for (String s : arg) {
            buffer.append(Objects.requireNonNull(convertObject(o)).getString(s));
        }
        return buffer.toString();
    }

}
