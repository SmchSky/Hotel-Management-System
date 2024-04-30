package com.hotelmanagementsystem.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    public static final long JWT_TTL = 60 * 60 * 1000L * 24 * 14;  // 有效期14天
    public static final String JWT_KEY = "SDFGjhdsfalshdfHFdsjkdsfds121232131afasdfac";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //用于OnlineUser
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    //用于StaffUser
    public static String createJWT(String subject, String duty) {
        JwtBuilder builder = getJwtBuilder(subject, duty, null, getUUID());
        return builder.compact();
    }

    //用于OnlineUser
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder().setId(uuid).setSubject(subject).setIssuer("sg").setIssuedAt(now).signWith(signatureAlgorithm, secretKey).setExpiration(expDate);
    }

    //用于StaffUser，利用username和duty生成JWT
    private static JwtBuilder getJwtBuilder(String subject, String duty, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }

        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("duty", duty);

        return Jwts.builder().setId(uuid).setIssuer("sg").setIssuedAt(now).signWith(signatureAlgorithm, secretKey).setExpiration(expDate).setClaims(claims);
    }

    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(JwtUtil.JWT_KEY);
        return new SecretKeySpec(encodeKey, 0, encodeKey.length, "HmacSHA256");
    }

    public static Claims parseJWT(String jwt) throws Exception {  //验证JWT是否有效
        SecretKey secretKey = generalKey();
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();
    }
}