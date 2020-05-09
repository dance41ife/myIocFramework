package com.company.proxy;

import com.company.annotations.Around;
import com.company.annotations.Before;
import com.company.enums.MethodAnnotationType;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ProxyFactory {

    private static ArrayList<Class> proxyAnnotations = new ArrayList<Class>() {{
        add(Before.class);
        add(Around.class);

    }};


    private static Object getProxy(String targetMethod, Object targetInstance, Method proxyMethod, Class superClass, MethodAnnotationType proxyType) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(superClass);

        MethodInterceptor interceptor = getInterceptorByType(targetMethod, targetInstance, proxyMethod,proxyType);

        enhancer.setCallback(interceptor);

        return enhancer.create();
    }

    public static Object getProxy(Method proxyMethod, Object instance) throws ClassNotFoundException {
        Class targetClass = null;
        AtomicReference<String> targetMethod = new AtomicReference<>("");
        AtomicReference<String> targetClassPath = new AtomicReference<>("");

        AtomicReference<MethodAnnotationType> type = new AtomicReference<>();
        for (Annotation methodAnnotation :
                proxyMethod.getAnnotations()) {
            if (proxyAnnotations.contains(methodAnnotation.getClass())) {

                Optional.ofNullable(getTargetClassPath(proxyMethod, methodAnnotation))
                        .ifPresent(annotation -> {
                            if (annotation instanceof Before) {
                                targetMethod.set(((Before) annotation).targetMethod());
                                targetClassPath.set(((Before) annotation).targetClass());
                                type.set(MethodAnnotationType.BEFORE);
                            } else if (annotation instanceof Around) {
                                targetClassPath.set(((Around) annotation).targetClass());
                                targetMethod.set(((Around) annotation).targetMethod());
                                type.set(MethodAnnotationType.AROUND);
                            }
                        });

                targetClass = Class.forName(targetClassPath.get());

            }
        }

        return getProxy(targetMethod.get(), instance, proxyMethod, targetClass, type.get());

    }

    private static Annotation getTargetClassPath(Method method, Annotation annotation) {
        if (annotation.annotationType() == Before.class) {
            return method.getAnnotation(Before.class);
        } else if (annotation.annotationType() == Around.class) {
            return method.getAnnotation(Around.class);
        }
        return null;
    }


    private static MethodInterceptor getInterceptorByType(String targetMethod, Object targetInstance, Method proxyMethod, MethodAnnotationType type) {
        switch (type) {
            case BEFORE:
                return new BeforeInterceptor(targetMethod, targetInstance, proxyMethod);
            default:
                return null;
        }
    }
}
