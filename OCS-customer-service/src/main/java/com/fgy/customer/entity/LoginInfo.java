package com.fgy.customer.entity;

import com.fgy.customer.enums.UserStateEnum;
import lombok.Data;

/**
 * @author fgy
 * description
 * date 2023/5/25 10:14
 */
@Data
public class LoginInfo {
    private String token;
    private String userId;
    private String userName;
    private Integer channelType;
    private Integer deviceType;
    private String queue;
    private UserStateEnum userState;
    private Long loginTime;
}
