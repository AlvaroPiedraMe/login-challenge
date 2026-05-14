# Frontend — Login Challenge

Angular 16 application for the Econocom technical challenge. Implements a login form connected to a Spring Boot JWT backend.

## Tech Stack

- Angular 16.2.16
- Angular Material 16.2.14
- SCSS — BEM methodology + Atomic Design
- Reactive Forms
- HTTP Client with JWT interceptor

## Project Structure

```
src/
├── app/
│   ├── auth/
│   │   ├── login/              # Login component (form, styles, template)
│   │   ├── models/             # LoginResponse and SsoResponse interfaces
│   │   ├── auth.service.ts     # HTTP calls + JWT localStorage management
│   │   ├── auth.guard.ts       # Route guard — redirects to /login if not authenticated
│   │   └── auth.interceptor.ts # Adds Authorization: Bearer <token> to all requests
│   ├── dashboard/              # Protected placeholder after login
│   ├── app-routing.module.ts   # Routes: /login, /dashboard (guarded)
│   └── app.module.ts
├── assets/
│   ├── figma-exports/          # Icons and logos from design
│   ├── fonts/Lato/             # Lato Regular, Bold, Black
│   └── scss/
│       ├── 1-settings/         # Design tokens: colors, spacing, typography
│       ├── 2-design-tokens/
│       ├── 3-tools/            # SCSS mixins
│       ├── 4-generic/          # Normalize and reset
│       ├── 5-elements/
│       ├── 6-skeleton/
│       ├── 7-components/
│       └── 8-utilities/
└── styles.scss                 # Global styles and font declarations
```

## Routes

| Route | Component | Protected |
|---|---|---|
| `/` | Redirects to `/login` | No |
| `/login` | LoginComponent | No |
| `/dashboard` | DashboardComponent | Yes (AuthGuard) |

## Running locally

```bash
npm install
ng serve
```

App available at `http://localhost:4200`. Requires backend running on `http://localhost:8080`.

## Build

```bash
ng build
```

Output in `dist/frontend/`.

## Authentication flow

1. User submits email + password on `/login`
2. `AuthService.login()` calls `POST /api/auth/login`
3. JWT and refresh token stored in `localStorage`
4. `AuthInterceptor` injects `Authorization: Bearer <token>` on every subsequent request
5. `AuthGuard` protects `/dashboard` — redirects to `/login` if no token found

## SSO flow

Clicking "ENTRAR CON SSO" calls `GET /api/auth/sso`, which simulates an SSO redirect and returns a JWT token.
