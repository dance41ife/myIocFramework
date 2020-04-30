package com.company.test;

import com.company.annotations.Service;

@Service
public class TestService {
    String msg = "ssss";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
