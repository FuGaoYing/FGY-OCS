package com.fgy.api.service.login;

import com.fgy.common.core.domain.vo.TokenVo;

/**
 * @author fgy
 * description
 * date 2023/5/23 15:12
 */
public interface UserLogin<T> {
    /**
     * 用户登录
     * @param t 入参
     * @return token
     */
    TokenVo login(T t);
}
