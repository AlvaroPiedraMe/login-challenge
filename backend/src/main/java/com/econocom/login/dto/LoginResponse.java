package com.econocom.login.dto;

/**
 * DTO for login responses.
 */
public class LoginResponse {

    private String token;
    private String refreshToken;
    private long expiresAt;

    public LoginResponse() {
    }

    public LoginResponse(String token, String refreshToken, long expiresAt) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }
}
