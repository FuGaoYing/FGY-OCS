package com.fgy.customer.manager;

import com.corundumstudio.socketio.SocketIOClient;
import com.fgy.common.security.utils.JwtUtils;
import com.fgy.customer.entity.LocalSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author FGY
 * @Description socket事件处理类
 * @Date Created in 2023/5/26 23:24
 * @Version
 */
@Component
@Slf4j
public class SocketEventProcessor {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void connectionEvent(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("auth");
        if (JwtUtils.validateToken(token)){
            String userKey = JwtUtils.getUserKey(token);
            String userId = JwtUtils.getUserId(token);
            if (StringUtils.hasLength(userKey)) {
                SocketIOClient socketIoClient = LocalSession.get(userKey);
                if (socketIoClient == null) {
                    log.info("用户 {} 进线 client id = {}",userId,client.getSessionId());
                    LocalSession.addSession(userKey, socketIoClient);
                } else {
                    // 重连
                    LocalSession.addSession(userKey, socketIoClient);
                }
            }
        }else {
            System.out.println("token无效" + token);
        }

    }

}
