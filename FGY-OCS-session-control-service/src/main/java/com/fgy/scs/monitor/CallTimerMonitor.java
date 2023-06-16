package com.fgy.scs.monitor;

import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.enums.StateEnums.SessionStateEnum;
import com.fgy.common.core.thread.OcsThreadFactory;
import com.fgy.common.redis.constant.RedisConstants;
import com.fgy.scs.actuator.CallActuator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fgy
 * description
 * date 2023/6/16 15:42
 */
@Component
public class CallTimerMonitor{
    private static final ScheduledExecutorService SCHEDULED = new ScheduledThreadPoolExecutor(1,new OcsThreadFactory("timer"));
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private CallActuator callActuator;
    @PostConstruct
    public void initialize(){
        SCHEDULED.scheduleAtFixedRate(() -> {
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(RedisConstants.ASSIGN_AGENT_TIME);
            if (!entries.isEmpty()) {
                entries.entrySet().stream().filter(e -> {
                    Long value = (Long)e.getValue();
                    return value  > System.currentTimeMillis() - 20 * 1000;
                }).forEach(entry -> {
                    String sessionId = entry.getKey().toString();
                    SessionInfo sessionInfo =  (SessionInfo) redisTemplate.opsForValue().get(RedisConstants.SESSION_INFO_KEY + sessionId);
                    if (sessionInfo != null){
                        sessionInfo.setSessionState(SessionStateEnum.WAITING_ALLOCATION_TIME_OUT);
                        callActuator.executeCallTasks(sessionInfo);
                    }
                });
            }
        },1,1, TimeUnit.SECONDS);
    }

}
