package com.fgy.customer.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fgy.common.security.utils.JwtUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fgy
 * description
 * date 2023/5/26 11:29
 */
@Component
public class SocketIoListener {

    public static ConcurrentHashMap<String,SocketIOClient> map = new ConcurrentHashMap<>();

    @OnConnect
    public void onConnect(SocketIOClient client) {
        String auth = client.getHandshakeData().getSingleUrlParam("auth");
        map.put(auth, client);
        System.out.println("用户连接" + auth);
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("用户"+ client.getSessionId() + "连接关闭");
        map.remove(client.getHandshakeData().getSingleUrlParam("auth"));
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
