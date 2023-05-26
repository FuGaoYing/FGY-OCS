package com.fgy.common.security.constant;

/**
 * @author fgy
 * description
 * date 2023/5/26 9:42
 */
public interface TokenConstants {

    /**
     * 令牌自定义标识
     */
    String AUTHENTICATION = "Authorization";

    /**
     * 令牌前缀
     */
    String PREFIX = "Bearer ";

    /**
     * 令牌秘钥
     */
    String SECRET = "abcdefghijklmnopqrstuvwxyz";
}
