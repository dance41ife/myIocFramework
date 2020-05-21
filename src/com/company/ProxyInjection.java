package com.company;

import com.company.annotations.Before;
import com.company.proxy.BeforeInterceptor;
import com.company.proxy.ProxyFactory;
import net.sf.cglib.proxy.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import static com.company.proxy.ProxyFactory.proxyAnnotations;

public class ProxyInjection {


    static {
        Map<Class<?>, Object> beanMap = BeanContainer.getBeanMap();
        for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
            Class<?> beanClass = entry.getKey();
            // 用于执行额外方法代理类的实例
            Object proxyInstance = entry.getValue();
            for (Method beanMethod : beanClass.getDeclaredMethods()) {
                if (beanMethod.getAnnotations().length == 0)
                    continue;
                for (Annotation a : beanMethod.getAnnotations()) {
                    try {
                        if (proxyAnnotations.contains(a.annotationType())) {
                            ProxyFactory.TargetClassWithProxyInstance proxyBean = ProxyFactory.getProxy(beanMethod, proxyInstance);
                            beanMap.put(proxyBean.getTargetClass(), proxyBean.getProxyInstance());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }


            }

        }
    }

}
