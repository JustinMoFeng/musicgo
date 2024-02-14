package com.music.education.Utils;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.sql.Date;
import java.util.Map;

public class JwtUtils {
    private static String signKey = "musiceducationapp";
    private static Long expireTime = 43200000L;

    /**
     * 生成JWT令牌
     * @param claims
     */
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, signKey)
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt
     */
    public static Claims parseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

}
