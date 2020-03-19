package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        HelperLoader.init();
        TestController TB = BeanHelper.getBean(TestController.class);
        System.out.println(TB.testService.getMsg());
    }
}
