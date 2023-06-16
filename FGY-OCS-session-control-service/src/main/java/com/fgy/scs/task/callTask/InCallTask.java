package com.fgy.scs.task.callTask;

import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.enums.StateEnums.SessionStateEnum;
import com.fgy.common.mq.constants.ExchangeConstants;
import com.fgy.common.mq.constants.RoutingKeyConstants;
import com.fgy.common.redis.constant.RedisConstants;
import com.fgy.scs.actuator.CallActuator;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


/**
 * @author fgy
 * description 入呼事件任务
 * date 2023/6/1 10:41
 */
@Slf4j
@Component
public class InCallTask implements CallTask{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    CallActuator callActuator;

    @Override
    public void execute(SessionInfo sessionInfo) {
        log.info("执行入呼事件任务 {}", sessionInfo);
        try {
            // 请求acd 申请坐席分配
            rabbitTemplate.convertSendAndReceive(ExchangeConstants.SCS_EXCHANGE, RoutingKeyConstants.SCS_ACD_ROUTING_KEY,
                    sessionInfo, new CorrelationData());
            // 保存会话流水
//            rabbitTemplate.convertSendAndReceive();
            // 通知客户端

        } catch (Exception e) {
            log.warn("用户 {} 入呼转人工失败，原因 {}",sessionInfo,e.getMessage());
            sessionInfo.setSessionState(SessionStateEnum.CALL_ACD_ERROR);
            callActuator.executeCallTasks(sessionInfo);
        }
        redisTemplate.opsForHash().put(RedisConstants.ASSIGN_AGENT_TIME, sessionInfo.getSessionId(),System.currentTimeMillis());
        sessionInfo.setSessionState(SessionStateEnum.WAITING_FOR_ALLOCATION);
        redisTemplate.opsForValue().set(RedisConstants.SESSION_INFO_KEY + sessionInfo.getSessionId(),sessionInfo);
    }

    @PostConstruct
    private void initialize() {
        CallTaskInfo.put(SessionStateEnum.IN_CALL_TRANSFER,this);
    }


}
