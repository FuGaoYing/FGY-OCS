package com.fgy.agent;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fgy
 * description
 * date 2023/5/22 9:35
 */
@SpringBootApplication
@EnableDubbo
public class AgentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgentServiceApplication.class,args);
    }
}