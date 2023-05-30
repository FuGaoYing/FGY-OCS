package com.fgy.scs.impl;

import com.fgy.api.service.scs.InCallService;
import com.fgy.common.core.domain.info.UserInfo;
import com.fgy.common.core.result.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author fgy
 * description
 * date 2023/5/30 19:44
 */
@DubboService
@Slf4j
public class InCallServiceImpl implements InCallService {
    @Override
    public CommonResult<String> inCall(UserInfo userInfo) {
        log.info("入呼事件入参 {}",userInfo);

        return null;
    }
}
