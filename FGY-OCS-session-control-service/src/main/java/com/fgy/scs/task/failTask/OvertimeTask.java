package com.fgy.scs.task.failTask;

import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.enums.StateEnums.SessionStateEnum;
import com.fgy.common.redis.constant.RedisConstants;
import com.fgy.scs.task.CallTask;
import com.fgy.scs.task.CallTaskInfo;
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
        // 清除会话信息
        redisTemplate.delete(RedisConstants.SESSION_INFO_KEY + sessionInfo.getSessionId());
        // 通知客户
        // 保存流水记录

    }

    @PostConstruct
    private void initialize() {
        CallTaskInfo.put(SessionStateEnum.WAITING_ALLOCATION_TIME_OUT,this);
    }
}
