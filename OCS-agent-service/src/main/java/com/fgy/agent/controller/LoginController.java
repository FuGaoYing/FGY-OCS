package com.fgy.agent.controller;

import com.fgy.api.service.login.LoginService;
import com.fgy.common.core.domain.req.AgentLoginReq;
import com.fgy.common.core.domain.vo.TokenVo;
import com.fgy.common.core.result.CommonResult;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fgy
 * description
 * date 2023/5/23 14:07
 */
@RestController
@RequestMapping("/agent")
public class LoginController {

    @Resource
    private LoginService<AgentLoginReq> loginService;
    @PostMapping("/login")
    public CommonResult<?> login(@Validated @RequestBody AgentLoginReq agentLoginReq) {
        TokenVo login = loginService.login(agentLoginReq);
        return CommonResult.ok(login);
    }
}
