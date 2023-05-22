package com.fgy.service;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author FGY
 * @Description
 * @Date Created in 2023/5/22 22:16
 * @Version
 */
@DubboService
public class TestImpl implements Test {
    @Override
    public String sayHello(String name) {
        return "hello";
    }
}
