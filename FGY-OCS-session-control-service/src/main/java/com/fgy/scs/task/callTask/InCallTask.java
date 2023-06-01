package com.fgy.scs.task.callTask;

import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.enums.StateEnums.SessionStateEnum;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author fgy
 * description 入呼事件任务
 * date 2023/6/1 10:41
 */
@Slf4j
@Component
public class InCallTask implements CallTask{

    @Override
    public void execute(SessionInfo sessionInfo) {
        log.info("执行入呼事件任务 {}", sessionInfo);
        // 申请坐席分配

        // 创建坐席分配定时器
    }

    @PostConstruct
    private void initialize() {
        CallTaskInfo.put(SessionStateEnum.IN_CALL_TRANSFER,new InCallTask());
    }
}
