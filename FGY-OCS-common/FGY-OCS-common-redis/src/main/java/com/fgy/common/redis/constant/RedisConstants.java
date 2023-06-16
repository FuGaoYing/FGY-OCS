package com.fgy.common.redis.constant;

/**
 * @author fgy
 * description
 * date 2023/5/25 14:41
 */
public interface RedisConstants {
    /**
     * 用户信息前缀
     */
    String USER_INFO_KEY = "userInfo-";
    /**
     * 进线客户量统计key
     */
    String ONLINE_QUANTITY = "online-quantity";

    /**
     * sessionInfo会话信息前缀
     */
    String SESSION_INFO_KEY = "sessionInfo-";

    /**
     * 分配坐席定时器
     */
    String ASSIGN_AGENT_TIME = "assign-agent-time";


}
