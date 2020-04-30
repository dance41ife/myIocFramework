package com.company;

import com.company.util.ClassUtil;

public final class IOCApplication {
    public static void init(){
        Class<?> [] classes = {
                IOCAppClassLoader.class,
                BeanContainer.class,
                PropertiesInjection.class
        };
        for(Class<?> cls : classes){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
