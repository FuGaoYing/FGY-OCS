package com.fgy.scs.impl;

import com.fgy.api.service.acd.AcdService;
import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author fgy
 * description
 * date 2023/6/16 16:03
 */
@DubboService
@Slf4j
public class AcdCallbackServiceImpl implements AcdService {
    @Override
    public CommonResult<Object> acdResponse(SessionInfo sessionInfo) {
        log.info("acd响应信息 {}", sessionInfo);
        return CommonResult.ok();
    }
}
