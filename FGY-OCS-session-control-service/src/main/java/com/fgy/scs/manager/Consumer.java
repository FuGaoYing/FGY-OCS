package com.fgy.scs.manager;

import com.alibaba.fastjson2.JSON;
import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.mq.constants.QueueConstants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author fgy
 * description
 * date 2023/6/16 9:41
 */
@Component
public class Consumer {

    @RabbitListener(queues = QueueConstants.SCS_ACD_QUEUE)
    public void test(Message message, Channel channel) throws IOException {
        byte[] body = message.getBody();
        SessionInfo sessionInfo = JSON.parseObject(body, SessionInfo.class);
        System.out.println(sessionInfo);
        channel.basicAck(1,true);
    }

}
