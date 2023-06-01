package com.fgy.customer.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.fgy.common.core.domain.OcsMessage;
import com.fgy.customer.manager.SocketEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    /**
     * 连接事件
     * @param client 连接
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        socketEventProcessor.connectionEvent(client);
    }

    /**
     * 挂断事件
     * @param client 连接
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        socketEventProcessor.onDisconnectEvent(client);
    }

    /**
     * 消息事件
     * @param client 连接
     * @param request ack回调
     * @param template 消息体
     */
    @OnEvent("message")
    public void onDataEvent(SocketIOClient client, AckRequest request, OcsMessage<String> template) {
        System.out.println(client.getSessionId());
    }

    /**
     * 转人工事件
     * @param client 连接
     * @param request ack回调
     * @param template 消息体
     */
    @OnEvent("incomingLine")
    public void onIncomingLineEvent(SocketIOClient client, AckRequest request, OcsMessage<String> template) {
        socketEventProcessor.onIncomingLineEvent(client,request,template);
    }

    /**
     * 挂机事件
     * @param client 连接
     * @param request ack回调
     * @param template 消息体
     */
    @OnEvent("close")
    public void oncloseEvent(SocketIOClient client, AckRequest request, OcsMessage<String> template) {
        System.out.println(client.getSessionId());
    }

}
