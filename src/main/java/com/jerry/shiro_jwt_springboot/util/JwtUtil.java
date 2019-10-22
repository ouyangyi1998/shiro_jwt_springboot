package com.jerry.shiro_jwt_springboot.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JwtUtil {
    //过期时间24小时
    private static final long EXPIRE_TIME=60*24*60*1000;
    //密钥
    private static final String SECRET="SHIRO+JWT";
    /*生成token,5min失效*/
    public static String createToken(String username)
    {
        try{
            Date date=new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm=Algorithm.HMAC256(SECRET);

            String token= JWT.create().withClaim("username",username).withExpiresAt(date).sign(algorithm);

            return token;
        }catch (UnsupportedEncodingException e)
        {
            return null;
        }
    }
    /*校验token*/
    public static boolean verify(String token,String username)
    {
        try{
            Algorithm algorithm=Algorithm.HMAC256(SECRET);
            JWTVerifier verifier=JWT.require(algorithm).withClaim("username",username).build();
            verifier.verify(token);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }
    /*获得token信息*/
    public static String getUsername(String token)
    {
        try{
            DecodedJWT jwt=JWT.decode(token);
            return jwt.getClaim("username").asString();
        }catch (JWTDecodeException e)
        {
            return null;
        }
    }
}
