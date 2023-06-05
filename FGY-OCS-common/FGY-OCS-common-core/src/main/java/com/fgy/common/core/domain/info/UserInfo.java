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
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     *  渠道类型
     */
    private Integer channelType;
    /**
     * 设备类型
     */
    private Integer deviceType;
    /**
     * 转接队列
     */
    private String queue;
    /**
     *  登录时间
     */
    private Long loginTime;
}
