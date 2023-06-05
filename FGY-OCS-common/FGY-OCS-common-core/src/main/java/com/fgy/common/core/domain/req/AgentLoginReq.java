package com.fgy.common.core.domain.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author fgy
 * description
 * date 2023/5/23 14:58
 */
@Data
public class AgentLoginReq{
    @NotEmpty(message = "坐席id不能为空")
    private Integer agentId;
    @NotEmpty(message = "坐席密码不能为空")
    private String password;
    @NotEmpty(message = "租户号不能为空")
    private String tenantId;
}
