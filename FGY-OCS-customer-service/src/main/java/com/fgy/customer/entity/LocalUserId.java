package com.fgy.customer.entity;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fgy
 * description
 * date 2023/5/30 17:34
 */
public class LocalUserId {
    private static final ConcurrentHashMap<String,String> LOCAL_USER = new ConcurrentHashMap<>();
    public static void addSession(String socketId, String userId) {
        LOCAL_USER.put(socketId, userId);
    }
    public static void remove(String socketId) {
        LOCAL_USER.remove(socketId);
    }
    public static String get(String socketId) {
        return LOCAL_USER.get(socketId);
    }
    public static boolean containsKey(String socketId) {
        return LOCAL_USER.containsKey(socketId);
    }

    public static int size() {
        return LOCAL_USER.size();
    }
}
