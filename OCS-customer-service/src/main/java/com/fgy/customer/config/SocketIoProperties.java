package com.fgy.customer.config;

import com.corundumstudio.socketio.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fgy
 * description
 * date 2023/5/26 10:59
 */
@ConfigurationProperties(prefix = "spring.data.socket")
public class SocketIoProperties extends Configuration {

}
