package com.company;

public final class HelperLoader {
    public static void init(){
        Class<?> [] classes = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class
        };
        for(Class<?> cls : classes){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
