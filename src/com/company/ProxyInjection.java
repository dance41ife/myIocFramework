package com.company;

import com.company.annotations.Before;
import com.company.proxy.BeforeInterceptor;
import com.company.proxy.ProxyFactory;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;
import java.util.Map;

public class ProxyInjection {


    static {
        Map<Class<?>, Object> beanMap = BeanContainer.getBeanMap();
        Class targetClass = null;
        //获得所有Bean类和Bean实例的关系
        for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
            //从beanMap中 取 Bean类和Bean实例
            Class<?> beanClass = entry.getKey();
            Object instance = entry.getValue();
            for (Method beanMethod :  beanClass.getDeclaredMethods()) {


                ProxyFactory.getProxy(beanMethod,instance);
                if (beanMethod.isAnnotationPresent(Before.class)) {
                    Before beforeAnnotation = beanMethod.getAnnotation(Before.class);
                    try {
                        targetClass = Class.forName(beforeAnnotation.targetClass());
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    String targetMethod = beforeAnnotation.targetMethod();
                    Enhancer enhancer = new Enhancer();
                    enhancer.setSuperclass(targetClass);

                    enhancer.setCallback(new BeforeInterceptor(targetMethod, instance, beanMethod));
                    beanMap.put(targetClass, enhancer.create());
                }
            }

        }
    }

}
