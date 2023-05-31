package com.fgy.scs.impl;

import com.fgy.api.service.scs.InCallService;
import com.fgy.common.core.domain.info.UserInfo;
import com.fgy.common.core.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @author fgy
 * description
 * date 2023/5/30 19:44
 */
@DubboService
@Slf4j
public class InCallServiceImpl implements InCallService {
    @Override
    public CommonResult<Object> inCall(UserInfo userInfo) {
        log.info("入呼事件入参 {}",userInfo);
        String remoteHost = RpcContext.getServerContext().getRemoteHost();
        System.out.println("调用方地址" + remoteHost);
        String token = RpcContext.getServerAttachment().getAttachment("auth");
        System.out.println(token);
        return CommonResult.ok();
    }
}
