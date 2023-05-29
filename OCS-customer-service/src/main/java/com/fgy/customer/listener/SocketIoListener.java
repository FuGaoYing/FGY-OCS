package com.fgy.customer.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fgy.common.security.utils.JwtUtils;
import com.fgy.customer.entity.LocalSession;
import com.fgy.customer.manager.SocketEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author fgy
 * description socket 事件消息监听
 * date 2023/5/26 11:29
 */
@Component
@Slf4j
public class SocketIoListener {

    @Autowired
    SocketEventProcessor socketEventProcessor;

    @OnConnect
    public void onConnect(SocketIOClient client) {
        socketEventProcessor.connectionEvent(client);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("auth");
        System.out.println("用户"+ client.getSessionId() + "连接关闭");
    }

    @OnEvent("message")
    public void onDataEvent(SocketIOClient client, AckRequest request, Object template) {
        System.out.println(client.getSessionId());
        if (request.isAckRequested()) {
            request.sendAckData("ack");
        }
        System.out.println("message" + template);
    }

    @OnEvent("test")
    public void onTestEvent(SocketIOClient client, AckRequest request, Object template) {
        System.out.println(client.getSessionId());
        System.out.println("test" + template);
    }
}
