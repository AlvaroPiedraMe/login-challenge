# Backend - Login Challenge

## Overview

Spring Boot backend for a JWT authentication system with support for direct login and simulated SSO flow.

The project is designed as a demonstration of clean MVC architecture, JWT security best practices, and clear separation of concerns.

## Tech Stack

- **Java 1.8** - Core language
- **Spring Boot 2.7.12** - Main framework
- **Spring Security** - Security and authentication management
- **Maven** - Dependency manager and build tool
- **jjwt 0.9.1** - JWT generation and validation

## Prerequisites

- Java 1.8 or higher
- Maven 3.6+

## Installation

### 1. Clone the repository

```bash
git clone https://github.com/AlvaroPiedraMe/login-challenge.git
cd login-challenge/backend
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Run the application

```bash
mvn spring-boot:run
```

The server starts at `http://localhost:8080`.

---

## Project Architecture

The application follows a **layered MVC architecture** to separate concerns:

```
src/main/java/com/econocom/login/
├── BackendApplication.java          # Application entry point
├── config/                          # Security and filter configuration
│   ├── SecurityConfig.java          # CORS and security rules
│   └── JwtAuthenticationFilter.java  # JWT filter for token validation
├── controller/                      # REST endpoints
│   └── AuthController.java          # Authentication endpoints
├── service/                         # Business logic
│   └── AuthService.java             # Auth, SSO, and refresh orchestration
├── repository/                      # Data access layer (in-memory)
│   └── UserRepository.java          # Hardcoded users
├── model/                           # Domain entities
│   └── User.java                    # User model
├── dto/                             # Data Transfer Objects
│   ├── LoginRequest.java            # Login request DTO
│   ├── LoginResponse.java           # Response with tokens
│   └── SsoResponse.java             # SSO response DTO
├── util/                            # Utilities
│   └── JwtUtil.java                 # JWT generation and validation
└── exception/                       # Error handling
    └── GlobalExceptionHandler.java  # Centralized exception handling
```

### Typical request flow:

1. **Client** → HTTP POST to `/api/auth/login` with credentials
2. **AuthController** → Receives the REST request
3. **AuthService** → Validates credentials and orchestrates the response
4. **JwtUtil** → Generates JWT tokens
5. **Client** → Receives access token + refresh token

---

## REST Endpoints

### 1. Direct login

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@econocom.com",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "expiresAt": 1689123456000
}
```

**Test credentials:**
- `user@econocom.com` / `password123` (role: USER)
- `admin@econocom.com` / `admin123` (roles: ADMIN, USER)

---

### 2. Initiate SSO flow

```http
GET /api/auth/sso
```

**Response (302 Found - Redirect):**
```
Location: /api/auth/sso/callback?code=SIMULATED_CODE
```

---

### 3. SSO Callback

```http
GET /api/auth/sso/callback?code=SIMULATED_CODE
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGc...",
  "message": "SSO successful"
}
```

---

### 4. Refresh token

```http
POST /api/auth/refresh?refreshToken=eyJhbGc...
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "expiresAt": 1689127056000
}
```

---

## Security

### JWT Configuration

- **Secret:** Configured in `application.properties`
- **Algorithm:** HS512
- **Access Token Expiration:** 1 hour (3600000 ms)
- **Refresh Token Expiration:** 7 days (604800000 ms)

### CORS

Allowed only from `http://localhost:4200` (Angular frontend).

### JWT Filter

The `JwtAuthenticationFilter` intercepts all requests, extracts the token from the `Authorization: Bearer <token>` header, and validates it. If valid, it loads the authentication into Spring Security.

---

## In-Memory Users

The application uses hardcoded users in `UserRepository` for demonstration purposes:

```java
user@econocom.com / password123  →  ROLE_USER
admin@econocom.com / admin123    →  ROLE_ADMIN, ROLE_USER
```

In production, this would be replaced with a real database and password hashing (bcrypt, etc).

---

## Configuration Properties

File: `src/main/resources/application.properties`

```properties
server.port=8080                           # Server port
jwt.secret=ReplaceThisSecretKeyForDemoPurposesOnly  # Secret for signing JWT
jwt.expiration.ms=3600000                  # Access token expiration (1h)
jwt.refresh.expiration.ms=604800000        # Refresh token expiration (7d)
```

---

## Error Handling

The application centralizes errors with `@ControllerAdvice`:

- **400 Bad Request:** Invalid credentials, expired token, incorrect SSO code
- **500 Internal Server Error:** Unexpected server errors

Typical error response:

```json
{
  "error": "Invalid credentials"
}
```

---

## Future Improvements (optional)

- [ ] Database integration (PostgreSQL, MongoDB)
- [ ] Password hashing with bcrypt
- [ ] Refresh token revocation list
- [ ] Rate limiting
- [ ] Unit and integration tests
- [ ] OpenAPI/Swagger documentation
- [ ] Structured logging

---

## Contributing

This project is a technical demonstration. Feel free to adapt the architecture to your needs.
