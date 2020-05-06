package com.company;

import com.company.test.TestController;
import com.company.test.TestTargetClass;

public class Main {

    public static void main(String[] args) {
	// write your code here
        IOCApplication.init();
        TestTargetClass targetClass = BeanContainer.getBean(TestTargetClass.class);
       targetClass.printClassName();
       targetClass.printHello();


    }
}
