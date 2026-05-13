package com.econocom.login.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * Utility class for JWT generation and validation.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.ms}")
    private long jwtExpirationMs;

    @Value("${jwt.refresh.expiration.ms}")
    private long refreshExpirationMs;

    /**
     * Generate an access token for a subject (email) with claims.
     *
     * @param subject subject (usually email)
     * @param claims  additional claims
     * @return generated JWT token
     */
    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Generate a refresh token for a subject.
     *
     * @param subject subject
     * @return refresh token string
     */
    public String generateRefreshToken(String subject) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + refreshExpirationMs);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .claim("type", "refresh")
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Validate a token and return claims if valid.
     *
     * @param token JWT token
     * @return claims
     * @throws JwtException for invalid tokens
     */
    public Claims validateToken(String token) throws JwtException {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Extract subject (email) from token.
     *
     * @param token JWT token
     * @return subject
     */
    public String extractSubject(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }

    /**
     * Check if token is a refresh token.
     *
     * @param token JWT token
     * @return true if refresh token
     */
    public boolean isRefreshToken(String token) {
        Claims claims = validateToken(token);
        Object type = claims.get("type");
        return type != null && "refresh".equals(type.toString());
    }

    /**
     * Get configured JWT access token expiration in milliseconds.
     *
     * @return expiration in ms
     */
    public long getJwtExpirationMs() {
        return jwtExpirationMs;
    }
}
