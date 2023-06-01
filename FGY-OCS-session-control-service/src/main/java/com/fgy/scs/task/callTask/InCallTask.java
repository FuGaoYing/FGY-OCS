package com.fgy.scs.task.callTask;

import com.fgy.common.core.domain.info.SessionInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fgy
 * description 入呼事件任务
 * date 2023/6/1 10:41
 */
@Slf4j
public class InCallTask implements CallTask{
    @Override
    public void execute(SessionInfo sessionInfo) {
        log.info("执行入呼事件任务 {}", sessionInfo);

    }
}
