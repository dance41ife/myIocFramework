package com.company.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AroundInterceptor extends BaseInterceptor {


    public AroundInterceptor(String targetMethod, Object targetInstance, Method targetInstanceMethod) {
        super(targetMethod, targetInstance, targetInstanceMethod);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (method.getName().equals(this.targetMethod)) {
            this.targetInstanceMethod.invoke(this.targetInstance);
            Object result =methodProxy.invokeSuper(o, objects);
            this.targetInstanceMethod.invoke(this.targetInstance);
            return  result;
        } else {
            return methodProxy.invokeSuper(o, objects);
        }
    }
}
