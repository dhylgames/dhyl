package com.yd.burst.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-31 19:54
 **/
public class JWTUtil {

    private static final String SECRET_KEY = "f9251e38-7f92-469a-8c04-7c8d2f9a7edc";

    public static String encode(String userId) {
        Integer ept = 10080;
        return JWTUtil.encode(userId, ept);
    }

    // 加密Token
    public static String encode(String playerId, Integer exceptionTime) {
        Map<String, Object> claims = new HashMap<>();
        long nowMillis = System.currentTimeMillis();
        long expirationMillis = nowMillis + exceptionTime * 60000L;
        claims.put("playerId", playerId);
        return Jwts.builder()
                .setSubject("subValue")
                .addClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(expirationMillis))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    // 解密Token
    public static String decode(String accessToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(accessToken).getBody();
            return (String) claims.get("playerId");
        } catch (Exception e) {  // 解密失败，返回null
            return null;
        }
    }
}
