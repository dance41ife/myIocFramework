package com.company;

import com.company.annotations.Before;
import com.company.annotations.Injection;
import com.company.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

public class ProxyInjection {


    static {
        Map<Class<?>, Object> beanMap = BeanContainer.getBeanMap();
        Class targetClass = null;
        //获得所有Bean类和Bean实例的关系
        for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
            //从beanMap中 取 Bean类和Bean实例
            Class<?> beanClass = entry.getKey();
            Object beanInstance = entry.getValue();
            //获取Bean类定义所有成员变量
            Field[] beanFields = beanClass.getDeclaredFields();
            for (Field beanField : beanFields) {
                if (beanField.isAnnotationPresent(Before.class)) {
                    Before beforeAnnotation = beanField.getAnnotation(Before.class);
                    try {
                        targetClass = Class.forName(beforeAnnotation.targetClass());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    String targetField = beforeAnnotation.targetMethod();


                }
            }
//            if(beanFields.length != 0){
//                //遍历成员变量 判断是否有带有Injection注解
//                for(Field beanField : beanFields){
//                    if(beanField.isAnnotationPresent(Injection.class)){
//                        //在bean map中获取bean field对应的实例
//                        Class<?> beanFieldClass = beanField.getType();
//                        Object beanFieldInstance = beanMap.get(beanFieldClass);
//                        if(beanFieldInstance != null){
//                            ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
//                        }
//                    }
//                }
//            }

        }
    }

}
