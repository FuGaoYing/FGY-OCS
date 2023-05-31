package com.fgy.common.core.domain.info;

import lombok.Data;
import java.io.Serializable;

/**
 * @author fgy
 * description
 * date 2023/5/30 19:26
 */
@Data
public class UserInfo implements Serializable {
    private String userId;
    private String userName;
    private Integer channelType;
    private Integer deviceType;
    private String queue;
    private Long loginTime;
}
