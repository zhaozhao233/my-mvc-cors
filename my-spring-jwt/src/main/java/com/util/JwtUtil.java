package com.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.Base64Utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    public static final String JWT_SECRET = "woyebuzhidaoxiediansha";

//    由字符串生成加密key
    public SecretKey generalKey() {
        String stringKey = JWT_SECRET;
        // 本地的密码解码
        byte[] encodeKey = Base64Utils.decodeFromString(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodeKey,0,encodeKey.length,"AES");

        return key;
    }

    // 创建jwt
    public String createJWT(String subject,long ttlMillis) {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部份内容封装好了
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 生成签名的时候使用的密钥secret，切记这个密钥不能外露哦。他就是你服务区端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里起始就是new一个JwtBuilder 设置 jwt 的 body
                //.setClaims(claims) // 如果由私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了哪些标准的声明的
                .setId(UUID.randomUUID().toString())    // 设置 jti(JWT ID) 是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)   // iat jwt的签发时间
                .setIssuer("www.nf.com")    // issuer jwt签发人
                .setSubject(subject)    // sub(Subject) 代表这个JWT的主题，即它的素有人，这个是一个json格式的字符串，可以存放什么userid,roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm,key);  // 设置签名使用的签名算法和签名使用的密钥

        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }


    // 解密jwt
    public Claims parseJWT(String jwt) {
        SecretKey key = generalKey();   // 签名密钥，和生成的签名的密钥一模一样
        Claims claims = Jwts.parser()   // 得到DefaultJwtParser
                .setSigningKey(key)     // 设置签名的密钥
                .parseClaimsJws(jwt).getBody(); // 设置需要解析的jwt
        return claims;
    }
}













