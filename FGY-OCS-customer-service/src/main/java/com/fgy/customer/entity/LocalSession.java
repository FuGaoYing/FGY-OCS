package com.fgy.customer.entity;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author FGY
 * @Description 本地会话信息
 * @Date Created in 2023/5/26 22:57
 * @Version
 */
public class LocalSession {
    private static final ConcurrentHashMap<String, SocketIOClient> LOCAL_MAP = new ConcurrentHashMap<>();

    public static void addSession(String userId,SocketIOClient client) {
        LOCAL_MAP.put(userId, client);
    }
    public static void removeSession(String userId) {
        LOCAL_MAP.remove(userId);
    }
    public static SocketIOClient get(String userId) {
        return LOCAL_MAP.get(userId);
    }
    public static boolean containsKey(String userId) {
        return LOCAL_MAP.containsKey(userId);
    }

    public static int size() {
        return LOCAL_MAP.size();
    }
}
