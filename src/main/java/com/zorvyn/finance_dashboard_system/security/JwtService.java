package com.zorvyn.finance_dashboard_system.security;

import com.zorvyn.finance_dashboard_system.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY = "mySecretKeyIsMyHardWork123456789";

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
        // 🔥 Generate Token (ID + Claims)
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("email",user.getEmail())
                .claim("role",user.getRoleType().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    // 🔹 Extract all claims
    private Claims extractAllClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    // 🔹 Extract User ID
    public Long extractUserId(String token) {
        return Long.parseLong(extractAllClaims(token).getSubject());
    }

    // 🔹 Extract Email
    public String extractEmail(String token) {
        return extractAllClaims(token).get("emailId", String.class);
    }

    // 🔹 Extract Role
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // 🔹 Validate Token
    public boolean isValid(String token, Long userId) {
        return extractUserId(token).equals(userId);
    }
}
