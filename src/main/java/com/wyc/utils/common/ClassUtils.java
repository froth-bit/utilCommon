package com.wyc.utils.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassUtils {

    /**
     * 通过反射获取bean对象里面的属性值
     * @param obj bean对象
     * @param fieldName 属性值，对象引用可以读出 .分割
     * @return
     * @throws IllegalAccessException
     */
    public static Object readAttributeValue(Object obj, String fieldName) throws IllegalAccessException {
        String [] fileNameArr = fieldName.split("\\.");
        Object o = obj;
        Field field;
        for (int i = 0; i < fileNameArr.length; i++) {
            field = getField(fileNameArr[i],o.getClass());
            //打开私有访问
            field.setAccessible(true);
            if(field.getName().equals(fileNameArr[i])) {
                if (i == fileNameArr.length - 1) {
                    return field.get(o);
                }
                o = field.get(o);
            }
        }
        return null;
    }

    /**
     * 获取field对象
     * @param fieldName field名字
     * @param clazz class
     */
    public static Field getField(String fieldName, Class<?> clazz){
        List<Field> fieldsList = new ArrayList<>();//保存所有的field
        do{  // 遍历所有父类字节码对象
            Field[] declaredFields = clazz.getDeclaredFields();  // 获取字节码对象的属性对象数组
            fieldsList.addAll(Arrays.asList(declaredFields));
            clazz = clazz.getSuperclass();  // 获得父类的字节码对象
        }while (clazz != null);
        //遍历field
        for (Field field:fieldsList){
            if(field.getName().equals(fieldName)){
                //找到了匹配的
                return field;
            }
        }
        return null;
    }

}
