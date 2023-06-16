package com.fgy.common.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 * @author fgy
 * description
 * date 2023/6/8 16:01
 */
@Configuration
public class RabbitMqConfig implements RabbitListenerConfigurer {

    @Bean
    public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer,ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 设置投递失败策略 true返回客户端 false 自动删除
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置确认回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (correlationData != null) {
                System.out.println(correlationData + "=====" + ack + "====" + cause);
            }
        });
        // 设置回调 接收返回消息
        rabbitTemplate.setReturnsCallback(System.out::println);
        configurer.configure(rabbitTemplate, connectionFactory);
        return  rabbitTemplate;
    }


    /**
     *  反序列化
     * @param registrar the registrar to be configured
     */
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory(){
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(new MappingJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }
}
