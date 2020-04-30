package com.company;

import com.company.test.TestController;

public class Main {

    public static void main(String[] args) {
	// write your code here
        IOCApplication.init();
        TestController TB = BeanContainer.getBean(TestController.class);
        System.out.println(TB.getTestService().getMsg());

        try {
            Class temp = Class.forName("com.company.test.TestController");
            System.out.println(temp.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
