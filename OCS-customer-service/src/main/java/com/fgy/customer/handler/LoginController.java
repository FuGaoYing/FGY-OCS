package com.fgy.customer.handler;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.SocketIOClient;
import com.fgy.api.service.login.UserLogin;
import com.fgy.common.core.domain.vo.TokenVo;
import com.fgy.common.core.result.CommonResult;
import com.fgy.customer.entity.req.UserReq;
import com.fgy.customer.listener.SocketIoListener;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
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
        SocketIOClient socketIOClient = SocketIoListener.map.get(token);
        socketIOClient.sendEvent("data", new AckCallback(Object.class) {
            @Override
            public void onSuccess(Object result) {
                System.out.println(result);
            }
        },"hahaha");
    }
}
