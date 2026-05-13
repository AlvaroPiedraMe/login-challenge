package com.econocom.login.service;

import com.econocom.login.dto.LoginResponse;
import com.econocom.login.model.User;
import com.econocom.login.repository.UserRepository;
import com.econocom.login.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service handling authentication and token logic.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Authenticate a user by email and password and return tokens on success.
     *
     * @param email    user email
     * @param password plain password
     * @return LoginResponse with tokens
     */
    public LoginResponse authenticate(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());

        String token = jwtUtil.generateToken(user.getEmail(), claims);
        String refresh = jwtUtil.generateRefreshToken(user.getEmail());

        long expiresAt = System.currentTimeMillis() + jwtUtil.getJwtExpirationMs();

        return new LoginResponse(token, refresh, expiresAt);
    }

    /**
     * Handle SSO callback simulation: validate code and return tokens.
     *
     * @param code sso code
     * @return LoginResponse with tokens
     */
    public LoginResponse handleSsoCallback(String code) {
        if (code == null || !"SIMULATED_CODE".equals(code)) {
            throw new IllegalArgumentException("Invalid SSO code");
        }

        // For demo, map SSO to user@econocom.com
        return authenticate("user@econocom.com", "password123");
    }

    /**
     * Refresh access token using a refresh token.
     *
     * @param refreshToken refresh token
     * @return new access token
     */
    public LoginResponse refreshAccessToken(String refreshToken) {
        try {
            if (!jwtUtil.isRefreshToken(refreshToken)) {
                throw new IllegalArgumentException("Invalid refresh token");
            }
            Claims claims = jwtUtil.validateToken(refreshToken);
            String subject = claims.getSubject();

            User user = userRepository.findByEmail(subject).orElseThrow(() -> new IllegalArgumentException("User not found for refresh"));

            Map<String, Object> accessClaims = new HashMap<>();
            accessClaims.put("roles", user.getRoles());

            String newToken = jwtUtil.generateToken(subject, accessClaims);
            String newRefresh = jwtUtil.generateRefreshToken(subject);
            long expiresAt = System.currentTimeMillis() + jwtUtil.getJwtExpirationMs();
            return new LoginResponse(newToken, newRefresh, expiresAt);

        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }
    }
}
