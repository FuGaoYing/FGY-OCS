package com.fgy.customer.manager;

import com.fgy.api.service.login.UserLogin;
import com.fgy.common.core.domain.vo.TokenVo;
import com.fgy.common.core.utils.IdUtils;
import com.fgy.common.redis.constant.RedisConstants;
import com.fgy.common.redis.constant.RedisConstants;
import com.fgy.common.security.constant.SecurityConstants;
import com.fgy.common.security.utils.JwtUtils;
import com.fgy.customer.entity.LoginInfo;
import com.fgy.customer.entity.req.UserReq;
import com.fgy.customer.enums.UserStateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;

import static com.fgy.common.redis.constant.RedisConstants.ONLINE_QUANTITY;

/**
 * @author fgy
 * description
 * date 2023/5/25 14:08
 */
@Slf4j
@Service("userManager")
public class UserManager implements UserLogin<UserReq> {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public TokenVo login(UserReq userReq) {
        String userId = userReq.getUserId();
        LoginInfo loginInfo = (LoginInfo) redisTemplate.opsForValue().get(RedisConstants.USER_INFO_KEY + userId);
        if (ObjectUtils.isEmpty(loginInfo)) {
            log.info("==========新用户进线 {}===========",userReq);
            // TODO 限制在线量
            Long num = (Long) redisTemplate.opsForValue().get(ONLINE_QUANTITY);
            loginInfo = new LoginInfo();
            BeanUtils.copyProperties(userReq, loginInfo);
            loginInfo.setUserState(UserStateEnum.INCOMING_LINE);
            loginInfo.setLoginTime(System.currentTimeMillis());
            HashMap<String, Object> map = new HashMap<>(4);
            Long userKey = IdUtils.getId();
            map.put(SecurityConstants.USER_KEY, userKey);
            map.put(SecurityConstants.DETAILS_USER_ID,loginInfo.getUserId());
            map.put(SecurityConstants.DETAILS_USERNAME,loginInfo.getUserName());
            String token = JwtUtils.createToken(map);
            loginInfo.setToken(token);
            redisTemplate.opsForValue().set(RedisConstants.USER_INFO_KEY + userId, loginInfo);
            redisTemplate.opsForValue().increment(ONLINE_QUANTITY,1);
            log.info("创建用户信息 {}",loginInfo);
            TokenVo tokenVo = new TokenVo();
            tokenVo.setAccessToken(token);
            return tokenVo;
        }
        UserStateEnum userState = loginInfo.getUserState();
        if (userState == UserStateEnum.IN_CALL || userState == UserStateEnum.ON_CALL) {
            log.info("用户已在线 {}", loginInfo);
            return null;
        }
        log.info("================用户重新进线===============");
        loginInfo.setLoginTime(System.currentTimeMillis());
        loginInfo.setUserState(UserStateEnum.INCOMING_LINE);
        String userKey = JwtUtils.getUserKey(loginInfo.getToken());
        redisTemplate.opsForValue().set(userKey,loginInfo);
        TokenVo tokenVo = new TokenVo();
        tokenVo.setAccessToken(loginInfo.getToken());
        return tokenVo;
    }
}
