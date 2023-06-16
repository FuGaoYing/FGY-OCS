package com.fgy.common.core.enums.StateEnums;

/**
 * @author fgy
 * description 会话状态
 * date 2023/6/1 10:05
 */
public enum SessionStateEnum {
    /**
     * 入呼转人工
     */
    IN_CALL_TRANSFER,
    /**
     * 等待坐席分配
     */
    WAITING_FOR_ALLOCATION,
    /**
     * 呼叫坐席
     */
    CALL_AGENT,

    CALL_ACD_ERROR,

    /**
     * 等待分配超时
     */
    WAITING_ALLOCATION_TIME_OUT,



}
