package com.fgy.scs.task.callTask;

import com.fgy.common.core.domain.info.SessionInfo;

/**
 * @author fgy
 * description
 * date 2023/6/1 10:38
 */
public interface CallTask {

    /**
     * 执行任务
     * @param sessionInfo 会话信息
     */
    void execute(SessionInfo sessionInfo);

}
