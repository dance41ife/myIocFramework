package com.company.test;

import com.company.annotations.Controller;
import com.company.annotations.Injection;

@Controller
public class TestController {
    @Injection
    TestService testService;

    public TestService getTestService() {
        return testService;
    }

    public void setTestService(TestService testService) {
        this.testService = testService;
    }
}
