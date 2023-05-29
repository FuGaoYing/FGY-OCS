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

    public static void addSession(String userKey,SocketIOClient client) {
        LOCAL_MAP.put(userKey, client);
    }
    public static void removeSession(String userKey) {
        LOCAL_MAP.remove(userKey);
    }
    public static SocketIOClient get(String userKey) {
        return LOCAL_MAP.get(userKey);
    }
    public static boolean containsKey(String userKey) {
        return LOCAL_MAP.containsKey(userKey);
    }

    public static int size() {
        return LOCAL_MAP.size();
    }
}
