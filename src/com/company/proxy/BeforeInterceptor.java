package com.company.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * 重写方法拦截在方法前和方法后加入业务
 * Object o为目标对象
 * Method method为目标方法
 * Object[] objects 为参数，
 * MethodProxy methodProxy CGlib方法代理对象
 */
public class BeforeInterceptor implements MethodInterceptor {

    private String targetMethod;
    private Object targetInstance;
    private Method targetInstanceMethod;

    public BeforeInterceptor(String targetMethod, Object targetInstance, Method targetInstanceMethod) {
        this.targetMethod = targetMethod;
        this.targetInstance = targetInstance;
        this.targetInstanceMethod = targetInstanceMethod;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (method.getName().equals(this.targetMethod)) {
            this.targetInstanceMethod.invoke(this.targetInstance);
            Object result = methodProxy.invokeSuper(o, objects);
            return result;
        } else {
            return methodProxy.invokeSuper(o, objects);
        }
    }
}
