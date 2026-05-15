# Frontend — Login Challenge

Angular 16 application for the Econocom technical challenge. Implements a login form connected to a Spring Boot JWT backend.

## Tech Stack

- Angular 16.2.16
- Angular Material 16.2.14
- SCSS — BEM methodology + Atomic Design
- Reactive Forms
- HTTP Client with JWT interceptor
- ngx-translate 15 — runtime i18n

## Project Structure

```
src/
├── app/
│   ├── auth/
│   │   ├── login/              # Login component (form, styles, template)
│   │   ├── models/             # LoginResponse and SsoResponse interfaces
│   │   ├── auth.service.ts     # HTTP calls + JWT localStorage management
│   │   ├── auth.guard.ts       # Route guard — redirects to /login if not authenticated
│   │   ├── no-auth.guard.ts    # Route guard — redirects to /dashboard if already authenticated
│   │   └── auth.interceptor.ts # Adds Authorization: Bearer <token> to all requests
│   ├── dashboard/              # Protected page shown after login
│   ├── sso-callback/           # Handles the SSO redirect from backend
│   ├── shared/
│   │   └── language.service.ts # Runtime language switching with localStorage persistence
│   ├── app-routing.module.ts   # Routes: /login, /sso/callback, /dashboard (guarded)
│   └── app.module.ts
├── assets/
│   ├── figma-exports/          # Icons and logos from design (includes login.svg favicon)
│   ├── fonts/Lato/             # Lato Regular (400), Bold (700), Black (900)
│   ├── i18n/                   # Translation files
│   │   ├── es.json
│   │   ├── en.json
│   │   ├── fr.json
│   │   └── pt.json
│   └── scss/
│       ├── 1-settings/         # Design tokens: colors, spacing, typography
│       ├── 2-design-tokens/
│       ├── 3-tools/            # SCSS mixins
│       ├── 4-generic/          # Normalize and reset
│       ├── 5-elements/
│       ├── 6-skeleton/
│       ├── 7-components/
│       └── 8-utilities/
└── styles.scss                 # Global styles, font declarations and typography hierarchy
```

## Routes

| Route | Component | Protected |
|---|---|---|
| `/` | Redirects to `/login` | No |
| `/login` | LoginComponent | No (NoAuthGuard: redirects to `/dashboard` if already logged in) |
| `/sso/callback` | SsoCallbackComponent | No |
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
6. After login the JWT payload is decoded to extract the user's email and role (ADMIN / USUARIO)

## SSO flow

1. Clicking "ENTRAR CON SSO" navigates the browser to `GET /api/auth/sso`
2. The backend issues a `302` redirect to `http://localhost:4200/sso/callback?code=SIMULATED_CODE`
3. `SsoCallbackComponent` reads the `code` query param and calls `GET /api/auth/sso/callback?code=...`
4. On success, the JWT is stored and the user is redirected to `/dashboard`

## Internationalisation (i18n)

Runtime language switching powered by **ngx-translate**. Translation files live in `src/assets/i18n/` and are loaded at runtime — no recompile needed.

Supported languages: **ES** · **EN** · **FR** · **PT**

`LanguageService` persists the selected language in `localStorage` under the key `app_language`. The login page footer shows a language dropdown; the currently active language is excluded from the options list.

## Typography

Lato font variants are applied globally via `styles.scss`:

| Weight | Variant | Used on |
|---|---|---|
| 400 | Lato Regular | Body text |
| 700 | Lato Bold | Labels, buttons, h2, h3 |
| 900 | Lato Black | h1 headings |
