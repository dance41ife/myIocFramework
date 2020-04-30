package com.company;

import com.company.util.ClassUtil;

public final class IOCApplication {
    public static void init(){
        Class<?> [] classes = {
                ClassHelper.class,
                BeanContainer.class,
                IocHelper.class
        };
        for(Class<?> cls : classes){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
