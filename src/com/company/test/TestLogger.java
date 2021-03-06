package com.company.test;

import com.company.annotations.Around;
import com.company.annotations.Bean;
import com.company.annotations.Before;

@Bean
public class TestLogger {

    @Before(targetClass = "com.company.test.TestTargetClass",targetMethod = "printHello")
    public void printTest(){
        System.out.println("before");
    }


    @Around(targetClass = "com.company.test.TestTargetClass", targetMethod = "printClassName")
    public void printLog (){
        System.out.println("around");
    }
}
