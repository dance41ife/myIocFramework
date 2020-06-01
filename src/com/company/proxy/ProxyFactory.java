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

    public static ArrayList<Class> proxyAnnotations = new ArrayList<Class>() {{
        add(Before.class);
        add(Around.class);

    }};

    /**
     *
     * @param superClass 需要被代理的实例的Class类型
     *
     */
    private static Object getProxy(String targetMethod, Object targetInstance, Method proxyMethod, Class superClass, MethodAnnotationType proxyType) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(superClass);

        MethodInterceptor interceptor = getInterceptorByType(targetMethod, targetInstance, proxyMethod, proxyType);

        enhancer.setCallback(interceptor);

        return enhancer.create();
    }

    /**
     * @param instance    需要被代理的实例
     * @param proxyMethod 需要被注入切面的方法，即在这个方法应该在被代理的实例执行时注入执行
     * @return TargetClassWithProxyInstance 包含了生成的代理实例以及目标实例的类
     */
    public static TargetClassWithProxyInstance getProxy(Method proxyMethod, Object instance) throws ClassNotFoundException {
        Class targetClass = null;
        AtomicReference<String> targetMethod = new AtomicReference<>("");
        AtomicReference<String> targetClassPath = new AtomicReference<>("");
        AtomicReference<MethodAnnotationType> type = new AtomicReference<>();


        for (Annotation methodAnnotation :
                proxyMethod.getAnnotations()) {

            Optional.ofNullable(getAnnotationType(proxyMethod, methodAnnotation))
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

        return new TargetClassWithProxyInstance(targetClass, getProxy(targetMethod.get(), instance, proxyMethod, targetClass, type.get()));

    }

    @SuppressWarnings("unchecked")
    private static Annotation getAnnotationType(Method method, Annotation annotation) {

        return method.getAnnotation(proxyAnnotations.get(proxyAnnotations.indexOf(annotation.annotationType())));
    }


    private static MethodInterceptor getInterceptorByType(String targetMethod, Object targetInstance, Method proxyMethod, MethodAnnotationType type) {
        switch (type) {
            case BEFORE:
                return new BeforeInterceptor(targetMethod, targetInstance, proxyMethod);
            case AROUND:
                return new AroundInterceptor(targetMethod, targetInstance, proxyMethod);
            default:
                return null;
        }
    }

    public static class TargetClassWithProxyInstance {
        private Class targetClass;
        private Object proxyInstance;

        public TargetClassWithProxyInstance(Class targetClass, Object proxyInstance) {
            this.targetClass = targetClass;
            this.proxyInstance = proxyInstance;
        }

        public Class getTargetClass() {
            return targetClass;
        }


        public Object getProxyInstance() {
            return proxyInstance;
        }

    }
}
