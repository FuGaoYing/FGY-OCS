package com.fgy.scs.impl;

import com.fgy.api.service.acd.AcdService;
import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.enums.StateEnums.SessionStateEnum;
import com.fgy.common.core.result.CommonResult;
import com.fgy.common.redis.constant.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author fgy
 * description
 * date 2023/6/16 16:03
 */
@DubboService
@Slf4j
public class AcdCallbackServiceImpl implements AcdService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public CommonResult<Object> acdResponse(SessionInfo agentInfo) {
        log.info("acd响应信息 {}", agentInfo);
        SessionInfo sessionInfo = (SessionInfo) redisTemplate.opsForValue().get(agentInfo.getSessionId());
        if (sessionInfo != null) {
            if (sessionInfo.getSessionState() == SessionStateEnum.IN_CALL_TRANSFER) {
                // 呼叫坐席
                // 记录流水
                sessionInfo.setSessionState(SessionStateEnum.CALL_AGENT);
                redisTemplate.opsForValue().set(RedisConstants.SESSION_INFO_KEY + sessionInfo.getSessionId(),sessionInfo);
            }
            return CommonResult.ok();
        }
        log.warn("会话 {} 不存在",agentInfo.getSessionId());
        return CommonResult.ok();
    }
}
