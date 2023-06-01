package com.fgy.scs.actuator;

import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.enums.StateEnums.SessionStateEnum;
import com.fgy.common.core.thread.OcsThreadFactory;
import com.fgy.scs.task.callTask.CallTask;
import com.fgy.scs.task.callTask.InCallTask;
import com.fgy.scs.task.SessionTask;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fgy
 * description 入呼执行器
 * date 2023/6/1 9:58
 */
@Component
public class CallActuator {

    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(1,2,
            10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(200),new OcsThreadFactory("scs"));
    private static final Map<SessionStateEnum,CallTask> CALL_TASK_MAP = new HashMap<>();
    @PostConstruct
    private void initMap() {
        CALL_TASK_MAP.put(SessionStateEnum.IN_CALL_TRANSFER,new InCallTask());
    }


    /**
     * 执行任务
     * @param sessionInfo 会话信息
     */
    public void executeCallTasks(SessionInfo sessionInfo) {
        // 获取执行任务
        CallTask callTask = CALL_TASK_MAP.get(sessionInfo.getSessionState());
        // 创建会话
        SessionTask sessionTask = new SessionTask(callTask, sessionInfo);
        // 执行任务
        POOL_EXECUTOR.execute(sessionTask);
    }


}
