package com.fgy.scs.task.callTask;

import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.enums.StateEnums.SessionStateEnum;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fgy
 * description 入呼事件任务
 * date 2023/6/1 10:41
 */
@Slf4j
@Component
public class InCallTask implements CallTask{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void execute(SessionInfo sessionInfo) {
        log.info("执行入呼事件任务 {}", sessionInfo);
        try {
            // 请求acd 申请坐席分配
            Object receive = rabbitTemplate.convertSendAndReceive(sessionInfo);
            // 保存会话流水
//            rabbitTemplate.convertSendAndReceive();
            // 通知客户端

        } catch (Exception e) {
            // 调用acd服务出问题 处理失败流程
            // 1. 结束会话
            // 2. 通知客户端
            // 3. 存储失败流失记录
        }
        // 创建坐席分配定时器
    }

    @PostConstruct
    private void initialize() {
        CallTaskInfo.put(SessionStateEnum.IN_CALL_TRANSFER,new InCallTask());
    }
}
