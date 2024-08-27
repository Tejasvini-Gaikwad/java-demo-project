package com.example.assetmanagementsystem.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.example.assetmanagementsystem.entities.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${token.signing.key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String userName = extractUsername(token);
        return (userName.equals(user.getUsername())) && isTokenExpired(token);
    }

    public String generateToken(Users user, Date expiryTime, String scope) {
        String ROLE = "role";
        return JWT.create().withSubject(user.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(expiryTime)
                .withClaim(ROLE, scope)
                .sign(Algorithm.HMAC256(secretKey));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private boolean isTokenExpired(String token) {
//        Date loginExpiryTime = DateConversionUtil.conversion(loginAudit);
//        return extractExpiration(token).equals(loginExpiryTime);
        return false;
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
    }

    private Key getSigningKey() {
        return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UserDetails getUserDetailsFromToken(String token, UserDetailsService userDetailsService) {
        String username = extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }

//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(getSigningKey())
//                .parseClaimsJws(token)
//                .getBody();
//
//        String username = claims.getSubject();
//        return null; // Replace with actual implementation
//    }
}
