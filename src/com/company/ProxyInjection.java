package com.company;

import com.company.annotations.Before;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Field;
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

            Method[] methods = beanClass.getDeclaredMethods();
            for (Method beanMethod : methods) {
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
                    enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
                        if (method.getName().equals(targetMethod)) {
                            beanMethod.invoke(instance, null);
                            Object result = methodProxy.invokeSuper(o, objects);
                            return result;
                        } else {
                            return methodProxy.invokeSuper(o, objects);
                        }

                    });
                    beanMap.put(targetClass, enhancer.create());
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
