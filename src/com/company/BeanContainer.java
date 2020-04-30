package com.company;

import com.company.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BeanContainer {
    /*定义bean映射 用于存放bean类与实例之间的映射关系*/
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>,Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> cls : beanClassSet){
            //实例化
            Object obj = ReflectionUtil.newInstance(cls);
            BEAN_MAP.put(cls,obj);
        }
    }

    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class "+ cls);
        }
        return (T)BEAN_MAP.get(cls);
    }
}
