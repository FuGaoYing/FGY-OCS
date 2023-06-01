package com.fgy.customer.handler;

import com.fgy.api.service.login.UserLogin;
import com.fgy.common.core.domain.vo.TokenVo;
import com.fgy.common.core.result.CommonResult;
import com.fgy.customer.entity.req.UserReq;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author fgy
 * description
 * date 2023/5/25 9:55
 */
@RestController
@RequestMapping("/customers")
@Slf4j
public class LoginController {

    @Resource(name = "userManager")
    private UserLogin<UserReq> userLogin;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/login")
    public CommonResult<TokenVo> login(@RequestBody UserReq userReq) {
        log.info("用户 {} 进线,入参 {}",userReq.getUserId(), userReq);
        TokenVo login = userLogin.login(userReq);
        if (login == null) {
            return CommonResult.fail(201,"重复进线");
        }
        return CommonResult.ok(login);
    }


    @GetMapping("/test")
    public void test(@RequestParam String token) {
    }
}
