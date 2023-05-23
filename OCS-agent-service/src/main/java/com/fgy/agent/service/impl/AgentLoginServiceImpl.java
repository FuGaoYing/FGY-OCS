package com.fgy.agent.service.impl;

import com.fgy.api.service.login.LoginService;
import com.fgy.common.core.domain.req.AgentLoginReq;
import com.fgy.common.core.domain.vo.TokenVo;
import org.springframework.stereotype.Service;

/**
 * @author fgy
 * description
 * date 2023/5/23 15:18
 */
@Service
public class AgentLoginServiceImpl implements LoginService<AgentLoginReq> {
    @Override
    public TokenVo login(AgentLoginReq agentLoginReq) {
        // 1. 获取坐席信息
        // 2. 生成tolken
        // 3. 返回
        return null;
    }
}
