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
public class BeforeInterceptor extends BaseInterceptor {


    public BeforeInterceptor(String targetMethod, Object targetInstance, Method targetInstanceMethod) {
        super(targetMethod,targetInstance,targetInstanceMethod);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (method.getName().equals(this.targetMethod)) {
            this.targetInstanceMethod.invoke(this.targetInstance);
            return methodProxy.invokeSuper(o, objects);
        } else {
            return methodProxy.invokeSuper(o, objects);
        }
    }
}
