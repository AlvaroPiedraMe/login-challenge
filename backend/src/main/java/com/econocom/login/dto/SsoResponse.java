package com.econocom.login.dto;

/**
 * DTO for SSO responses.
 */
public class SsoResponse {

    private String token;
    private String message;

    public SsoResponse() {
    }

    public SsoResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
