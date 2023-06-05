package com.fgy.common.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fgy.common.security.constant.SecurityConstants;
import java.util.Date;

import static com.fgy.common.security.constant.TokenConstants.EXPIRE_TIME;
import static com.fgy.common.security.constant.TokenConstants.SECRET;

/**
 * @author fgy
 * description token 工具类
 * date 2023/5/25 20:24
 */
public class JwtUtils {

    /**
     * 生成token
     * @param userId 数据声明
     * @param userName 数据声明
     * @return 令牌
     */
    public static String createToken (String userId,String userName) {
        // 签名算法
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        // 构造JWT
        long currentTimeMillis = System.currentTimeMillis();
        long expireTimeMillis = currentTimeMillis + EXPIRE_TIME * 1000;
        return JWT.create()
                .withClaim(SecurityConstants.DETAILS_USER_ID, userId)
                .withClaim(SecurityConstants.DETAILS_USERNAME, userName)
                .withIssuedAt(new Date(currentTimeMillis))
                .withExpiresAt(new Date(expireTimeMillis))
                .sign(algorithm);
    }


    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserId(String token) {
        DecodedJWT decode = JWT.decode(token);
        return decode.getClaim(SecurityConstants.DETAILS_USER_ID).asString();
    }

    /**
     * 根据令牌获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUserName(String token) {
        DecodedJWT decode = JWT.decode(token);
        return decode.getClaim(SecurityConstants.DETAILS_USERNAME).asString();
    }



    /**
     * 检测token是否有效
     * @param token token
     * @return 有效true
     */
    public static boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            // 验证不通过
            return false;
        }
    }

}
