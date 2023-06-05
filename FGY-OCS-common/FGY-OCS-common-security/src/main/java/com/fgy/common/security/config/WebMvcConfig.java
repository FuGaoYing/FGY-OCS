package com.fgy.common.security.config;

import com.fgy.common.security.Interceptor.GlobalHandlerInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fgy
 * description
 * date 2023/5/23 15:54
 */
@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalHandlerInterceptor())
                .addPathPatterns("/**");
    }
}
