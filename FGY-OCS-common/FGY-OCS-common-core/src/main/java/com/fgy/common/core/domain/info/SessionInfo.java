package com.fgy.common.core.domain.info;

import com.fgy.common.core.enums.StateEnums.SessionStateEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fgy
 * description 会话实体类
 * date 2023/5/25 14:14
 */
@Data
public class SessionInfo implements Serializable {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 坐席id
     */
    private String agentId;
    /**
     * 队列
     */
    private String query;
    /**
     *  渠道类型
     */
    private Integer channelType;
    /**
     * 设备类型
     */
    private Integer deviceType;
    /**
     * 全局会话Id
     */
    private Long sessionId;
    /**
     * 回调地址,确保发往客户端正确
     */
    private String remoteHost;
    /**
     * 会话状态
     */
    private SessionStateEnum sessionState;
}
