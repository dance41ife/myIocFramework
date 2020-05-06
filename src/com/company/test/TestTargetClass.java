package com.company.test;

import com.company.annotations.Bean;

@Bean
public class TestTargetClass {

    public void printClassName() {
        System.out.println("TestTargetClass");
    }

    public void printHello(){
        System.out.println("hello");
    }
}
