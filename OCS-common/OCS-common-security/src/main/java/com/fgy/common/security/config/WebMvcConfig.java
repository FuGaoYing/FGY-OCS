package com.fgy.common.security.config;

import com.fgy.common.core.Interceptor.GlobalHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fgy
 * description
 * date 2023/5/23 15:54
 */
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GlobalHandlerInterceptor())
                .addPathPatterns("/**");
    }
}
