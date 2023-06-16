package com.fgy.scs.config;

import com.fgy.common.mq.constants.ExchangeConstants;
import com.fgy.common.mq.constants.QueueConstants;
import com.fgy.common.mq.constants.RoutingKeyConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author fgy
 * description
 * date 2023/6/9 14:34
 */
@Configuration
public class RabbitMqConfig {

    /**
     * scs服务交换机
     * @return 交换机
     */
    @Bean(name = ExchangeConstants.SCS_EXCHANGE)
    public Exchange exchange() {
        HashMap<String, Object> arguments = new HashMap<>(8);
        return ExchangeBuilder.topicExchange(ExchangeConstants.SCS_EXCHANGE)
                .durable(true)
                .autoDelete()
                .withArguments(arguments)
                .build();
    }

    @Bean(name = QueueConstants.SCS_ACD_QUEUE)
    public Queue queue() {
        return QueueBuilder.durable(QueueConstants.SCS_ACD_QUEUE).build();
    }

    @Bean
    public Binding binding(@Qualifier(QueueConstants.SCS_ACD_QUEUE) Queue queue,
                            @Qualifier(ExchangeConstants.SCS_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RoutingKeyConstants.SCS_ACD_ROUTING_KEY).noargs();
    }
}
