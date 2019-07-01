package com.example.rocketmq.service;

import org.springframework.stereotype.Service;

@Service
public class ExceptionService {

    public boolean getThrowable(){
        System.out.println("处理业务逻辑");
        throw new RuntimeException();
    }
}
