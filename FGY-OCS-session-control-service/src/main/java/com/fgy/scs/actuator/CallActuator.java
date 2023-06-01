package com.fgy.scs.actuator;

import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.enums.StateEnums.SessionStateEnum;
import com.fgy.common.core.thread.OcsThreadFactory;
import com.fgy.scs.task.callTask.CallTask;
import com.fgy.scs.task.callTask.CallTaskInfo;
import com.fgy.scs.task.SessionTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author fgy
 * description 入呼执行器
 * date 2023/6/1 9:58
 */
@Component
@Slf4j
public class CallActuator {

    private static final ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(1,2,
            10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(200),new OcsThreadFactory("scs"));


    /**
     * 执行任务
     * @param sessionInfo 会话信息
     */
    public void executeCallTasks(SessionInfo sessionInfo) {
        // 获取执行任务
        CallTask callTask = CallTaskInfo.get(sessionInfo.getSessionState());
        // 创建会话
        SessionTask sessionTask = new SessionTask(callTask, sessionInfo);
        // 执行任务
        POOL_EXECUTOR.execute(sessionTask);
        log.info("执行任务 {} {}",sessionInfo,callTask);

    }


}
