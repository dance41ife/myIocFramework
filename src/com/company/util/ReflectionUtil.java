package com.company.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {
    Object instance;
    /*
    * 创建实例
    * */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try{
            instance = cls.newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return  instance;
    }
    /*
     *调用方法
     * */
    public static Object invokeMethod(Object object, Method method,Object args){
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(object,args);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }
    /*
    设置成员变量的值
     */
    public static void setField(Object obj, Field field,Object value){
        try{
            field.setAccessible(true);
            field.set(obj,value);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}

