import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader, TRANSLATE_HTTP_LOADER_CONFIG } from '@ngx-translate/http-loader';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SsoCallbackComponent } from './sso-callback/sso-callback.component';
import { AuthInterceptor } from './auth/auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    SsoCallbackComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    TranslateModule.forRoot({
      defaultLanguage: 'es',
      loader: {
        provide: TranslateLoader,
        useClass: TranslateHttpLoader
      }
    })
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: TRANSLATE_HTTP_LOADER_CONFIG, useValue: { prefix: './assets/i18n/', suffix: '.json' } }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
