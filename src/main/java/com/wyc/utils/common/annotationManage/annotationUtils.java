package com.wyc.utils.common.annotationManage;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class annotationUtils {

    /**
     * 获取注解实例
     * @param ae 可以使用注解的元素
     * @param annotationClass 得到哪个注解
     * @param <A> 返回注解
     * @return
     */
    public static <A extends Annotation> A getAnnotation(AnnotatedElement ae, Class<A> annotationClass){
        return ae.getAnnotation(annotationClass);
    }

}
