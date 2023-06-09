package com.fgy.scs.task.callTask;

import com.fgy.common.core.enums.StateEnums.SessionStateEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fgy
 * description 会话任务对象容器 存储会话任务对象
 * date 2023/6/1 16:02
 */
public class CallTaskInfo {
    private static final Map<SessionStateEnum,CallTask> CALL_TASK_MAP = new HashMap<>();

    public static CallTask get(SessionStateEnum stateEnum){
        return CALL_TASK_MAP.get(stateEnum);
    }
    public static void put(SessionStateEnum stateEnum,CallTask callTask) {
        CALL_TASK_MAP.put(stateEnum,callTask);
    }
}
