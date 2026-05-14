import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from './models/login-response.model';
import { SsoResponse } from './models/sso-response.model';

const API_URL = 'http://localhost:8080/api/auth';
const TOKEN_KEY = 'auth_token';
const REFRESH_TOKEN_KEY = 'auth_refresh_token';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${API_URL}/login`, { email, password }).pipe(
      tap(response => this.storeTokens(response.token, response.refreshToken))
    );
  }

  ssoLogin(): Observable<SsoResponse> {
    return this.http.get<SsoResponse>(`${API_URL}/sso`).pipe(
      tap(response => this.storeTokens(response.token, null))
    );
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(REFRESH_TOKEN_KEY);
  }

  private storeTokens(token: string, refreshToken: string | null): void {
    localStorage.setItem(TOKEN_KEY, token);
    if (refreshToken) {
      localStorage.setItem(REFRESH_TOKEN_KEY, refreshToken);
    }
  }
}
