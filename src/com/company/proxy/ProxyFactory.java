package com.company.proxy;

import com.company.annotations.Around;
import com.company.annotations.Before;
import com.company.enums.MethodAnnotationType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ProxyFactory {

    private static ArrayList<Class> proxyAnnotations = new ArrayList<Class>() {{
        add(Before.class);
        add(Around.class);

    }};


    private static Object getProxy(String targetMethod, Object targetInstance, Method proxyMethod, Class superClass) {


        return new Object();
    }

    public static Object getProxy(Method proxyMethod, Object instance) {
        Class targetClass = null;
        String targetMethod = "";
        for (Annotation methodAnnotation :
                proxyMethod.getAnnotations()) {
            if (proxyAnnotations.contains(methodAnnotation.getClass())){
                targetMethod =getPath(proxyMethod,methodAnnotation).targetMethod();
            }
        }

//        if (proxyMethod.isAnnotationPresent(Before.class)) {
//            Before beforeAnnotation = proxyMethod.getAnnotation(Before.class);
//            targetMethod = beforeAnnotation.targetMethod();
//            try {
//                targetClass = Class.forName(beforeAnnotation.targetClass());
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

        return getProxy(targetMethod, instance, proxyMethod, targetClass);

    }

    private static  Annotation getTargetClassPath(Method method,Annotation annotation) {
        if (annotation.annotationType() == Before.class){
            return method.getAnnotation(Before.class);
        }
        else if (annotation.annotationType() == Around.class){
            return  method.getAnnotation(Around.class);
        }

    }

    private static Before getPath(Method method,Annotation annotation){
        return (Before)getTargetClassPath(method,annotation);
    }
}
