package com.fgy.customer.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fgy
 * description
 * date 2023/5/26 11:05
 */
@Configuration
@EnableConfigurationProperties(SocketIoProperties.class)
public class SocketIoConfig {
    @Autowired
    private SocketIoProperties properties;
    @Bean
    public SocketIOServer socketIoServer() {
        return new SocketIOServer(properties);
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner() {
        return new SpringAnnotationScanner(socketIoServer());
    }
}
