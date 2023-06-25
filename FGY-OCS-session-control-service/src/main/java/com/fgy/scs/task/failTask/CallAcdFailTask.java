package com.fgy.scs.task.failTask;

import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.scs.task.CallTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author fgy
 * description
 * date 2023/6/25 16:04
 */
@Slf4j
@Component
public class CallAcdFailTask implements CallTask {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public void execute(SessionInfo sessionInfo) {
        //
    }
}
