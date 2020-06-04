package com.study.netty.service;

import org.springframework.stereotype.Service;

/**
 * @author YanCh
 * @version v1.0
 * Create by 2020-06-04 下午4:52
 **/
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hello, " + name;
    }
}
