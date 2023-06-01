package com.fgy.customer.manager;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.fgy.api.service.scs.InCallService;
import com.fgy.common.core.domain.OcsMessage;
import com.fgy.common.core.domain.info.UserInfo;
import com.fgy.common.core.enums.ResultCodeEnum;
import com.fgy.common.core.result.CommonResult;
import com.fgy.common.redis.constant.RedisConstants;
import com.fgy.common.security.utils.JwtUtils;
import com.fgy.customer.entity.LocalSession;
import com.fgy.customer.entity.LocalUserId;
import com.fgy.customer.entity.LoginInfo;
import com.fgy.customer.enums.UserStateEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import static com.fgy.common.redis.constant.RedisConstants.ONLINE_QUANTITY;

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

    @Autowired
    private InCallService inCallService;
    /**
     * 连接事件处理
     * @param client
     */
    public void connectionEvent(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("auth");
        if (JwtUtils.validateToken(token)){
            String userId = JwtUtils.getUserId(token);
            log.info("用户 {} 进线 socketId {}", userId,client.getSessionId().toString());
            if (StringUtils.hasLength(userId)) {
                LocalUserId.addSession(client.getSessionId().toString(),userId);
                LoginInfo loginInfo = (LoginInfo) redisTemplate.opsForValue().get(RedisConstants.USER_INFO_KEY + userId);
                if (ObjectUtils.isEmpty(loginInfo) || loginInfo.getUserState() == UserStateEnum.END) {
                    log.warn("用户 {} 未登录",userId);
                    client.disconnect();
                    return;
                }
                log.info("用户 {} 重连 token {} ",userId,token);
                // 如果重连每次覆盖掉原有client
                LocalSession.addSession(userId, client);
                return;
            }
            log.warn(" userId{} 为空 ",userId);
        }else {
            client.disconnect();
            log.warn("token无效  {}",token);
        }

    }

    /**
     * 断开事件处理
     * @param client
     */
    public void onDisconnectEvent(SocketIOClient client) {
        String userId = LocalUserId.get(client.getSessionId().toString());
        log.info("socket断开事件 userId = {}",userId);
        try {
            if (StringUtils.hasLength(userId)) {
                LoginInfo loginInfo = (LoginInfo) redisTemplate.opsForValue().get(RedisConstants.USER_INFO_KEY + userId);
                if (loginInfo != null) {
                    UserStateEnum userState = loginInfo.getUserState();
                    // 用户离线
                    if (userState == UserStateEnum.IN_CALL || userState == UserStateEnum.ON_CALL) {
                        log.info("用户 {} 离线 ", userId);
                        // TODO 通知坐席用户离线
                        return;
                    }
                    redisTemplate.delete(RedisConstants.USER_INFO_KEY + userId);
                    redisTemplate.opsForValue().decrement(ONLINE_QUANTITY);
                    client.disconnect();
                }
                LocalSession.removeSession(userId);
            }
        } catch (Exception e) {
            log.error("异常 ",e);
        } finally {
            LocalUserId.remove(client.getSessionId().toString());
        }

    }

    public void onIncomingLineEvent(SocketIOClient client, AckRequest request,OcsMessage<String> template) {
        String userId = LocalUserId.get(client.getSessionId().toString());
        log.info("用户 {} 发起转人工",userId);
        LoginInfo loginInfo = (LoginInfo) redisTemplate.opsForValue().get(RedisConstants.USER_INFO_KEY + userId);
        if (loginInfo != null) {
            if (loginInfo.getUserState() == UserStateEnum.INCOMING_LINE) {
                // 发起转人工
                loginInfo.setUserState(UserStateEnum.IN_CALL);
                UserInfo userInfo = new UserInfo();
                BeanUtils.copyProperties(loginInfo,userInfo);
                RpcContext.getClientAttachment().setAttachment("auth",loginInfo.getToken());
                CommonResult<Object> result = inCallService.inCall(userInfo);
                if (result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
                    // 更新用户状态
                    redisTemplate.opsForValue().set(RedisConstants.USER_INFO_KEY + userId,loginInfo);
                }
                extracted(request,result.getData());
                return;
            }
            log.info("用户 {} 重复转人工",userId);
        }
        log.info("用户 {} 未登录", userId);
    }


    private static void extracted(AckRequest request,Object o) {
        if (request.isAckRequested()) {
            request.sendAckData(o);
        }
    }
}
