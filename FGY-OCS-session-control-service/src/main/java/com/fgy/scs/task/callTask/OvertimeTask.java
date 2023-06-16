package com.fgy.scs.task.callTask;

import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.enums.StateEnums.SessionStateEnum;
import com.fgy.common.redis.constant.RedisConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author fgy
 * description  超时任务处理
 * date 2023/6/16 15:53
 */
@Component
public class OvertimeTask implements CallTask {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void execute(SessionInfo sessionInfo) {
        redisTemplate.delete(RedisConstants.SESSION_INFO_KEY + sessionInfo.getSessionId());

    }

    @PostConstruct
    private void initialize() {
        CallTaskInfo.put(SessionStateEnum.WAITING_ALLOCATION_TIME_OUT,this);
    }
}
