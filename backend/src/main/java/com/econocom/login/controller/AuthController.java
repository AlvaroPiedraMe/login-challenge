package com.econocom.login.controller;

import com.econocom.login.dto.LoginRequest;
import com.econocom.login.dto.LoginResponse;
import com.econocom.login.dto.SsoResponse;
import com.econocom.login.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for authentication endpoints.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Handle login requests.
     *
     * @param request login request with email and password
     * @return JWT token and refresh token on success
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.authenticate(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    /**
     * Simulate initiating an SSO flow by redirecting to callback with a simulated code.
     *
     * @return redirect response
     */
    @GetMapping("/sso")
    public ResponseEntity<Void> sso() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "http://localhost:4200/sso/callback?code=SIMULATED_CODE");
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    /**
     * Simulated SSO callback endpoint.
     *
     * @param code sso code
     * @return JWT token if code valid
     */
    @GetMapping("/sso/callback")
    public ResponseEntity<SsoResponse> ssoCallback(@RequestParam(value = "code", required = false) String code) {
        LoginResponse login = authService.handleSsoCallback(code);
        SsoResponse ssoResponse = new SsoResponse(login.getToken(), "SSO successful");
        return ResponseEntity.ok(ssoResponse);
    }

    /**
     * Endpoint to refresh tokens using a refresh token.
     *
     * @param refreshToken refresh token provided in query param
     * @return new tokens
     */
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestParam("refreshToken") String refreshToken) {
        LoginResponse response = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }
}
