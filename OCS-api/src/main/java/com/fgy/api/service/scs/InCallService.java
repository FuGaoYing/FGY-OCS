package com.fgy.api.service.scs;


import com.fgy.common.core.domain.info.UserInfo;
import com.fgy.common.core.result.CommonResult;

/**
 * @author fgy
 * description
 * date 2023/5/30 19:12
 */

public interface InCallService {
    CommonResult<Object> inCall(UserInfo userInfo);
}
