package com.fgy.customer.entity.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.ToString;

/**
 * @author fgy
 * description
 * date 2023/5/25 10:08
 */
@Data
@ToString
public class UserReq {
    /**
     * 用户id
     */
    @NotEmpty(message = "用户名不能为空")
    private String userId;
    /**
     * 用户名
     */

    private String userName;
    /**
     *  渠道
     */
    private Integer channelType;
    /**
     *  设备
     */
    private Integer deviceType;

}
