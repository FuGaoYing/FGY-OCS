package com.fgy.common.security.utils;

import com.fgy.common.security.constant.SecurityConstants;
import io.jsonwebtoken.*;

import java.util.Map;

import static com.fgy.common.security.constant.TokenConstants.SECRET;

/**
 * @author fgy
 * description token 工具类
 * date 2023/5/25 20:24
 */
public class JwtUtils {

    /**
     * 生成token
     * @param claims 数据声明
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserKey(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.USER_KEY);
    }

    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserId(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }

    /**
     * 根据令牌获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUserName(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }

    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key 键
     * @return 值
     */
    public static String getValue(Claims claims, String key) {
        return claims.get(key) == null ? "" : String.valueOf(claims.get(key));
    }

    /**
     * 检测token是否有效
     * @param token token
     * @return 有效true
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            // Token 有效
            return true;
        } catch (SignatureException e) {
            // Token 无效，签名信息不匹配
            return false;
        } catch (Exception e) {
            // Token 无效或过期
            return false;
        }
    }

}
