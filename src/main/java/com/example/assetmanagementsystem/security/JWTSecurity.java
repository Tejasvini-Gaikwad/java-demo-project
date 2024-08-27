//package com.example.assetmanagementsystem.security;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.Claim;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.spec.SecretKeySpec;
//import java.security.Key;
//import java.util.Date;
//import java.util.function.Function;
//
//@Service
//@RequiredArgsConstructor
//public class JWTService {
//
//    private final String jwtKey = "${token.signing.key}";
//
//    public String extractUserName(String token) {
//        return extractClaim(token, Claim::getSubject);
//    }
//
//    public boolean isTokenValid(String token, UserDetails user, LoginAudit loginAudit) {
//        final String userName = extractUserName(token);
//        return (userName.equals(user.getUsername())) && isTokenExpired(token, loginAudit);
//    }
//
//    public String generateToken(UserDetails user, Date expiryTime, String scope) {
//        String ROLE = "role";
//        return JWT.create().withSubject(user.getUsername())
//                .withIssuedAt(new Date(System.currentTimeMillis()))
//                .withExpiresAt(expiryTime)
//                .withClaim(ROLE, scope)
//                .sign(Algorithm.HMAC256(jwtKey));
//    }
//
//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolvers.apply(claims);
//    }
//
//    private boolean isTokenExpired(String token, LoginAudit loginAudit) {
//        Date loginExpiryTime = DateConversionUtil.conversion(loginAudit);
//        return extractExpiration(token).equals(loginExpiryTime);
//    }
//
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
//    }
//
//    private Key getSigningKey() {
//        return new SecretKeySpec(jwtKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
//    }
//}
