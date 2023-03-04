package com.me.yimu.pay.utils;

import java.util.Calendar;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTTest {

	public static void main(String[] args) {
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.SECOND, 90);
		
		// 生成JWT
		String token = JWT.create()
				.withClaim("username", "张三") // 设置自定义用户名
				.withExpiresAt(instance.getTime()) // 设置过期时间
				.sign(Algorithm.HMAC256("token!Q2W#E$RW")); //设置签名 保密 
		System.out.println(token);
		
		// 根据令牌和签名解析数据
		JWTVerifier jWTVerifier = 
			JWT.require(Algorithm.HMAC256("token!Q2W#E$RW")).build();
		DecodedJWT decodedJWT = jWTVerifier.verify(token);
		System.out.println("用户名: " + decodedJWT.getClaim("username").asString());
		System.out.println("过期时间: "+decodedJWT.getExpiresAt());
		
		byte[] header =
		        java.util.Base64.getDecoder().decode("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
		System.out.println(new String(header));
	}

}
