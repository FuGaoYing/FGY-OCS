package com.fgy.common.core.domain.info;

import lombok.Data;

/**
 * @author fgy
 * description
 * date 2023/5/30 19:26
 */
@Data
public class UserInfo {
    private String token;
    private String userId;
    private String userName;
    private Integer channelType;
    private Integer deviceType;
    private String queue;
    private Long loginTime;
}
