package com.lxn.task_manager.auth.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private String secretKey = "FifTP7FBYfsismu-ibgyOaLb2EM6QMf4LSxJHbDT7O4="; // Thay thế bằng khóa bí mật của bạn
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    public String createToken(String username) {
        try {
            Map<String, Object> claims = new HashMap<>();
            // 1 giờ
            long validityInMilliseconds = 3600000;
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
        } catch (Exception exception) {
            logger.error("Error ->"+ exception.getMessage());
        }
        return null;
    }

    public boolean validateToken(String token, String username) {
        String user = extractUsername(token);
        return (user.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}

