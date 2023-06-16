package com.fgy.scs;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fgy
 * description
 * date 2023/5/30 19:42
 */
@SpringBootApplication
@EnableDubbo
public class ScsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScsApplication.class,args);
    }
}
