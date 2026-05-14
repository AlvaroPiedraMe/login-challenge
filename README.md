# Login Challenge — Econocom

Full-stack login application built as a technical challenge for Econocom. Implements JWT authentication, SSO simulation, and a protected dashboard.

## Overview

The application consists of two independent services:

- **Backend** — REST API built with Spring Boot that handles authentication, issues JWT tokens and manages a refresh token flow.
- **Frontend** — Angular SPA that presents the login form, stores the JWT and navigates to a protected dashboard on success.

```
┌─────────────────────┐        HTTP / JSON        ┌──────────────────────┐
│   Angular 16        │ ◄────────────────────────► │   Spring Boot        │
│   localhost:4200    │                             │   localhost:8080     │
└─────────────────────┘                             └──────────────────────┘
```

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 2.x, Java 1.8, Maven |
| Authentication | JWT (jjwt), Spring Security |
| Frontend | Angular 16.2.16 |
| Styles | SCSS — BEM + Atomic Design |
| Forms | Angular Reactive Forms |

## Project Structure

```
login-challenge/
├── backend/     # Spring Boot — authentication API
└── frontend/    # Angular — login UI and dashboard
```

## Running the project

### 1. Backend

Requires Java 1.8 and Maven installed.

```bash
cd backend
mvn spring-boot:run
```

API available at `http://localhost:8080`.

### 2. Frontend

Requires Node.js and Angular CLI.

```bash
cd frontend
npm install
ng serve
```

App available at `http://localhost:4200`.

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/login` | Authenticate with email and password. Returns JWT + refresh token. |
| GET | `/api/auth/sso` | Initiates simulated SSO flow. |
| GET | `/api/auth/sso/callback` | SSO callback. Returns JWT on valid code. |
| POST | `/api/auth/refresh` | Exchange refresh token for new access token. |

## Test credentials

| Email | Password | Role |
|---|---|---|
| `user@econocom.com` | `password123` | USER |
| `admin@econocom.com` | `admin123` | ADMIN, USER |

## Authentication flow

1. User submits credentials on `/login`
2. Backend validates credentials and returns `{ token, refreshToken, expiresAt }`
3. Frontend stores tokens in `localStorage`
4. `AuthInterceptor` injects `Authorization: Bearer <token>` on every request
5. `AuthGuard` protects `/dashboard` — redirects to `/login` if no token present

## Branch strategy

| Branch | Purpose |
|---|---|
| `main` | Stable, deployable code |
| `ft/frontend` | Frontend maquetación (merged) |
| `ft/integration` | Frontend–backend integration (merged) |
