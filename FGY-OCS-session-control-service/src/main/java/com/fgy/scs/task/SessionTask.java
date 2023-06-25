package com.fgy.scs.task;

import com.fgy.common.core.domain.info.SessionInfo;

/**
 * @author fgy
 * description
 * date 2023/6/1 11:31
 */
public class SessionTask implements Runnable{
    private final CallTask callTask;
    private final SessionInfo sessionInfo;
    public SessionTask(CallTask callTask,SessionInfo sessionInfo) {
        this.callTask = callTask;
        this.sessionInfo = sessionInfo;
    }
    @Override
    public void run() {
        callTask.execute(sessionInfo);
    }
}
