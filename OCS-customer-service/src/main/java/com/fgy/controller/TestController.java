package com.fgy.controller;

import com.fgy.service.Test;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author FGY
 * @Description
 * @Date Created in 2023/5/22 22:19
 * @Version
 */
@RestController
public class TestController {
    @DubboReference
    private Test test;

    @GetMapping("/test")
    public String test() {
        return test.sayHello("Hello");
    }
}
