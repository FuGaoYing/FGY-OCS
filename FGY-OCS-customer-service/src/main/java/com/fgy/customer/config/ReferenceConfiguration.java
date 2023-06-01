package com.fgy.customer.config;

import com.fgy.api.service.scs.InCallService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fgy
 * description
 * date 2023/5/30 20:01
 */
@Configuration
public class ReferenceConfiguration {
    @Bean
    @DubboReference()
    public ReferenceBean<InCallService> helloService() {
        return new ReferenceBean<>();
    }
}
