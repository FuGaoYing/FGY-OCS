package com.fgy.scs.impl;

import com.fgy.api.service.scs.InCallService;
import com.fgy.common.core.constants.TermConstant;
import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.domain.info.UserInfo;
import com.fgy.common.core.enums.ResultCodeEnum;
import com.fgy.common.core.enums.StateEnums.SessionStateEnum;
import com.fgy.common.core.result.CommonResult;
import com.fgy.common.core.utils.IdUtils;
import com.fgy.common.redis.constant.RedisConstants;
import com.fgy.common.security.utils.JwtUtils;
import com.fgy.scs.actuator.CallActuator;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author fgy
 * description
 * date 2023/5/30 19:44
 */
@DubboService
@Slf4j
public class InCallServiceImpl implements InCallService {
    @Autowired
    CallActuator inCallActuator;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public CommonResult<Object> inCall(UserInfo userInfo) {
        log.info("远程调用入呼接口入参 {}",userInfo);
        String remoteHost = RpcContext.getServerContext().getRemoteHost();
        System.out.println("调用方地址" + remoteHost);
        String token = RpcContext.getServerAttachment().getAttachment("auth");
        // 参数校验
        if (inCallVerification(token,userInfo)) {
            // 创建会话信息
            SessionInfo sessionInfo = new SessionInfo();
            // 全局会话Id
            Long sessionId = IdUtils.getId();
            sessionInfo.setSessionId(sessionId);
            sessionInfo.setUserId(userInfo.getUserId());
            sessionInfo.setUserName(userInfo.getUserName());
            sessionInfo.setChannelType(userInfo.getChannelType());
            sessionInfo.setDeviceType(userInfo.getDeviceType());
            sessionInfo.setRemoteHost(remoteHost);
            sessionInfo.setSessionState(SessionStateEnum.IN_CALL_TRANSFER);
            // 存储会话信息
            redisTemplate.opsForValue().set(RedisConstants.SESSION_INFO_KEY + sessionId,sessionInfo);
            inCallActuator.executeCallTasks(sessionInfo);
            return CommonResult.ok(TermConstant.TRANSFER_TERM,sessionId);
        }
        return CommonResult.fail(ResultCodeEnum.PERMISSION_ERROR.getCode(),ResultCodeEnum.PERMISSION_ERROR.getMessage());
    }


    /**
     * 参数校验
     * @param token token
     * @param userInfo 入参
     * @return
     */
    private boolean inCallVerification(String token, UserInfo userInfo) {
        if (JwtUtils.validateToken(token)) {
            String userId = JwtUtils.getUserId(token);
            String userName = JwtUtils.getUserName(token);
            return userId.equals(userInfo.getUserId()) && userName.equals(userInfo.getUserName());
        }
        return false;
    }

}
