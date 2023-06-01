package com.fgy.api.service.acd;

import com.fgy.common.core.domain.info.SessionInfo;
import com.fgy.common.core.result.CommonResult;

/**
 * @author fgy
 * description
 * date 2023/6/1 16:24
 */
public interface AcdService {
    CommonResult<Object> assignAgent(SessionInfo sessionInfo);
}
