package com.lxn.task_manager.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String secretKey; // Thay thế bằng khóa bí mật của bạn

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username,1000 * 60 * 60 * 10);
    }

    public String generateRefreshToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, 1000L * 60 * 60 * 24 * 30);
    }

    private String createToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Hết hạn sau 10 giờ
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token, String email) {
        String user = extractEmail(token);
        return (user.equals(email) && isTokenExpired(token));
    }

    public boolean validateToken(String token) {
        String email = extractEmail(token);
        return !email.isEmpty() && isTokenExpired(token);
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return !extractAllClaims(token).getExpiration().before(new Date());
    }
}


