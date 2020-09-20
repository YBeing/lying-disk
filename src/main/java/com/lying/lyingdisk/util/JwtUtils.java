package com.lying.lyingdisk.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtils {
    /**
     * 获取token
     */
    public static String getToken(String username, String password){
        long now =System.currentTimeMillis();
        long expirDate=1000*60*60*36 + now;
        JwtBuilder builder = Jwts.builder().setId(username)
                .setSubject(username)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,username.getBytes())
                .setExpiration(new Date(expirDate));
        return builder.compact();
    }
    /**
     * 通过token获取username
     */
    public static String getUsername(String token){
        String username = null;
        try {
            username = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 验证token
     */
    public static boolean checkToken(String token,String username){
        try {
            Claims claims = Jwts.parser().setSigningKey(username.getBytes()).parseClaimsJws(token).getBody();
            String id = claims.getId();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
