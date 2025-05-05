import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { HttpErrorResponse } from '@angular/common/http';
import { catchError, tap, throwError } from 'rxjs';

import { AuthHttpService } from './auth-http.service';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthManager {
  constructor(
    private authHttp: AuthHttpService,
    private authState: AuthService
  ) {}

  login(email: string, password: string): Observable<string> {
    return this.authHttp.login(email, password).pipe(
      tap((token) => this.authState.setToken(token)),
      catchError((err: HttpErrorResponse) => {
        const isUnauthorized = err.status === 401;
        const fallback = isUnauthorized
          ? 'Credenciales incorrectas. Verifica tu usuario y contraseña.'
          : 'Error al iniciar sesión.';
        return this.handleError(err, fallback);
      })
    );
  }

  register(
    name: string,
    surname: string,
    email: string,
    password: string,
    carPlate: string
  ): Observable<string> {
    return this.authHttp
      .register(email, name, surname, password, carPlate)
      .pipe(
        tap((result) => this.authState.setRegister(result)),
        catchError((err) =>
          this.handleError(err, 'Error al registrar el usuario.')
        )
      );
  }

  confirmEmail(token: string): Observable<string> {
    return this.authHttp.confirmEmail(token).pipe(
      tap((msg) => this.authState.setConfirmEmail(msg)),
      catchError((err) =>
        this.handleError(err, 'No se pudo confirmar el correo electrónico.')
      )
    );
  }

  requestPasswordReset(email: string): Observable<string> {
    return this.authHttp.requestPasswordReset(email).pipe(
      catchError((err: HttpErrorResponse) => {
        const isUnauthorized = err.status === 401;
  
        const fallbackMessage = 'No se pudo enviar el correo de restablecimiento.';
  
        if (isUnauthorized) {
          return throwError(() => new Error(fallbackMessage));
        }
  
        return this.handleError(err, fallbackMessage);
      })
    );
  }
  

  requestNewPassword(token: string, password: string): Observable<string> {
    console.log('🟢 Enviando requestNewPassword con token:', token);

    return this.authHttp.requestNewPassword(token, password).pipe(
      catchError((err) =>
        this.handleError(err, 'No se pudo restablecer la contraseña.')
      )
    );
  }

  logout(): void {
    this.authState.logout();
  }

  getToken(): string | null {
    return this.authState.getToken();
  }

  getRole(): string | null {
    return this.authState.getRole();
  }

  getName(): string | null {
    return this.authState.getName();
  }

  // Método privado para manejar errores
  private handleError(
    err: HttpErrorResponse,
    defaultMessage?: string
  ): Observable<never> {
    let message = defaultMessage ?? 'Ha ocurrido un error inesperado.';

    if (typeof err.error === 'string' && err.error.trim() !== '') {
      message = err.error;
    } else if (err.error?.message) {
      message = err.error.message;
    }

    console.error('Auth error:', err);
    return throwError(() => new Error(message));
  }
}
