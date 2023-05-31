package com.fgy.common.core.domain.info;

import lombok.Data;

import java.io.Serializable;

/**
 * @author fgy
 * description 会话实体类
 * date 2023/5/25 14:14
 */
@Data
public class SessionInfo implements Serializable {
    private String userId;
    private String userName;
    private String agentId;
    private String query;
    private Integer channelType;
    private Integer deviceType;
    private Long jobId;
}
