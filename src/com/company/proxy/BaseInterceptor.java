package com.company.proxy;

import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

public abstract class BaseInterceptor implements MethodInterceptor {

    String targetMethod;
    Object targetInstance;
    Method targetInstanceMethod;

    public BaseInterceptor(String targetMethod, Object targetInstance, Method targetInstanceMethod) {
        this.targetMethod = targetMethod;
        this.targetInstance = targetInstance;
        this.targetInstanceMethod = targetInstanceMethod;
    }
}
