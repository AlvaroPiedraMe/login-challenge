# Login Challenge — Econocom

Full-stack login application built as a technical challenge for Econocom. Implements JWT authentication, SSO simulation, protected dashboard, runtime i18n and deployment on Render.

## Live demo

| Service | URL |
|---|---|
| **Frontend** | https://login-challenge-frontend.onrender.com |
| **Backend** | https://login-challenge-1.onrender.com |

> The backend runs on Render's free tier and spins down after periods of inactivity. The first request after a cold start may take up to 50 seconds. Subsequent requests respond normally.

## Test credentials

| Email | Password | Role |
|---|---|---|
| `user@econocom.com` | `password123` | USUARIO |
| `admin@econocom.com` | `admin123` | ADMIN |

## Overview

The application consists of two independent services:

- **Backend** — REST API built with Spring Boot that handles authentication, issues JWT tokens and manages a refresh token flow.
- **Frontend** — Angular SPA that presents the login form, stores the JWT and navigates to a protected dashboard on success.

```
┌──────────────────────────────────────┐        HTTP / JSON        ┌──────────────────────┐
│   Angular 16                         │ ◄────────────────────────► │   Spring Boot        │
│   login-challenge-frontend.onrender  │                             │   login-challenge-1  │
└──────────────────────────────────────┘                             └──────────────────────┘
```

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 2.x, Java 1.8, Maven, Docker |
| Authentication | JWT (jjwt), Spring Security |
| Frontend | Angular 16.2.16 |
| Styles | SCSS — BEM + Atomic Design |
| Forms | Angular Reactive Forms |
| i18n | ngx-translate (ES, EN, FR, PT) |
| Fonts | Lato Regular / Bold / Black |
| Deployment | Render (static site + web service) |

## Project Structure

```
login-challenge/
├── backend/     # Spring Boot — authentication API + Dockerfile
└── frontend/    # Angular — login UI and dashboard
```

## Running locally

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

## Authentication flow

1. User submits credentials on `/login`
2. Backend validates credentials and returns `{ token, refreshToken, expiresAt }`
3. Frontend stores tokens in `localStorage`
4. `AuthInterceptor` injects `Authorization: Bearer <token>` on every request
5. `AuthGuard` protects `/dashboard` — redirects to `/login` if no token present

## SSO flow

1. Clicking "ENTRAR CON SSO" navigates the browser to `GET /api/auth/sso`
2. Backend issues a `302` redirect to the frontend `/sso/callback?code=SIMULATED_CODE`
3. `SsoCallbackComponent` reads the code and calls `GET /api/auth/sso/callback?code=...`
4. On success, JWT is stored and the user is redirected to `/dashboard`

## i18n

Runtime language switching powered by ngx-translate. Supported languages: **ES · EN · FR · PT**. The selected language persists across sessions via `localStorage`.

## Branch strategy

| Branch | Purpose |
|---|---|
| `main` | Stable, deployable code |
| `ft/frontend` | Frontend maquetación (merged) |
| `ft/integration` | Frontend–backend integration (merged) |
