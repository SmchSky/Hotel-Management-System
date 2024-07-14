package com.hotelmanagementsystem.backend.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
@PropertySource({"classpath:jwtConfig.properties"})
public class JwtUtil {
    /**
     * token有效期(14天)（实例变量），不将jwtTtl和jwtMaterialString设置为静态变量的原因是：@Value注解无法对静态变量赋值！！！
     */
    @Value("${jwt.ttl}")
    private long jwtTtl;
    /**
     * 密钥生成材料（实例变量）
     */
    @Value("${jwt.keyMaterialString}")
    private String jwtMaterialString;
    
    /**
     * token有效期（静态变量）
     */
    private static long JWT_TTL;
    
    /**
     * 密钥（静态变量）
     */
    private static SecretKey secretKey;
    
    /**
     * 初始化2个静态变量，包括生成秘钥的过程。@PostConstruct注解用在init方法上，表示init方法会在Spring初始化此Bean并完成依赖注入后执行，用于完成一些初始化操作（比如）
     * 此方法中：将实例变量的值赋给静态变量，本类静态方法只需要使用token有效期和秘钥，故只设置两个静态变量即可！
     */
    @PostConstruct
    public void init() {
        JWT_TTL = this.jwtTtl;
        // 将JWT_STRING（一个随便指定的字符串）进行Base64解码，生成一组随机的二进制数据，这组数据就是生成最终秘钥的秘钥材料
        byte[] keyMaterial = Base64.getDecoder().decode(this.jwtMaterialString);
        // 使用秘钥材料和HmacSHA256算法生成一个SecretKey对象（包含了秘钥）
        secretKey = new SecretKeySpec(keyMaterial, 0, keyMaterial.length, "HmacSHA256");
    }
    
    
    /**
     * 生成一个随机的UUID字符串
     */
    private static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 为线上用户创建JWT
     *
     * @param username 用户名
     * @return JWT
     */
    public static String createJwtTokenForOnlineUser(String username) {
        return createJwtToken(username, getUUID(), null);
    }
    
    /**
     * 为酒店工作人员用户创建JWT
     *
     * @param username 用户名
     * @param duty     职务
     * @return JWT
     */
    public static String createJwtTokenForStaffUser(String username, String duty) {
        return createJwtToken(username, getUUID(), duty);
    }
    
    /**
     * 创建JWT
     *
     * @param subject 用户名
     * @param uuid    UUID
     * @param duty    职务
     * @return JWT
     */
    private static String createJwtToken(String subject, String uuid, @Nullable String duty) {
        // 加密算法为HS256，也就是HmacSHA256，一种对称加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 计算token的过期时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + JwtUtil.JWT_TTL;
        Date expire = new Date(expMillis);
        // 创建JwtBuilder
        JwtBuilder builder = Jwts.builder().setId(uuid).setSubject(subject).setIssuer("smy").setIssuedAt(now).signWith(secretKey, signatureAlgorithm).setExpiration(expire);
        if (duty != null) {
            builder.claim("duty", duty);
        }
        return builder.compact();
    }
    
    /**
     * 验证token是否有效（包括是否过期）
     *
     * @param token token
     */
    public static Claims parseJWT(String token) throws JwtException {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}