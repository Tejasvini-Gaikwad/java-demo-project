package com.example.assetmanagementsystem.security;//package com.example.assetmanagementsystem.security;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.auth0.jwt.interfaces.JWTVerifier;
//
//import java.util.Date;
//
//public class JWTUtil {
//
//    private static final String SECRET_KEY = "abcdefghijklmnopqrstuvwxyz";
//    private static final long EXPIRATION_TIME = 86400000; // 15 minutes
//    private static final String ISSUER = "com.example.assetmanagementsystem";
//
//    public static String generateToken(String username) {
//        return JWT.create()
//                .withSubject(username)
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .sign(Algorithm.HMAC256(SECRET_KEY));
//    }
//
//    public String validateToken(String token) {
//        return JWT.require(Algorithm.HMAC512(SECRET_KEY.getBytes()))
//                .build()
//                .verify(token)
//                .getSubject();
//    }
//}
//

import io.jsonwebtoken.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtUtil {

    private String secretKey = "vgvgvgvgvvgbhbhh";

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }
}
