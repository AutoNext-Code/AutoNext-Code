import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { HttpErrorResponse } from '@angular/common/http';
import { catchError, tap, throwError } from 'rxjs';

import { AuthHttpService } from './auth-http.service';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthManager {
  constructor(
    private authHttp: AuthHttpService,
    private authState: AuthService
  ) {}

  login(email: string, password: string): Observable<string> {
    return this.authHttp.login(email, password).pipe(
      tap(token => this.authState.setToken(token)),
      catchError(err => this.handleError(err, 'Credenciales incorrectas.'))
    );
  }

  register(name: string, surname: string, email: string, password: string, carPlate: string): Observable<string> {
    return this.authHttp.register(email, name, surname, password, carPlate).pipe(
      tap(result => this.authState.setRegister(result)),
      catchError(err => this.handleError(err, 'Error al registrar el usuario.'))
    );
  }

  confirmEmail(token: string): Observable<string> {
    return this.authHttp.confirmEmail(token).pipe(
      tap(msg => this.authState.setConfirmEmail(msg)),
      catchError(err => this.handleError(err, 'Error al confirmar el correo.'))
    );
  }

  requestPasswordReset(email: string): Observable<string> {
    return this.authHttp.requestPasswordReset(email).pipe(
      tap(msg => this.authState.setConfirmEmail(msg)),
      catchError(err => this.handleError(err, 'Error al solicitar el restablecimiento de contraseña.'))
    );
  }

  requestNewPassword(password: string): Observable<string> {
    return this.authHttp.requestNewPassword(password).pipe(
      tap(msg => this.authState.setConfirmEmail(msg)),
      catchError(err => this.handleError(err, 'Error al restablecer la contraseña.'))
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
  private handleError(err: HttpErrorResponse, fallbackMsg: string): Observable<never> {
    let message = fallbackMsg;

    if (err.status === 401) {
      message = 'Credenciales incorrectas. Verifica tu usuario y contraseña.';
    } else if (typeof err.error === 'string') {
      message = err.error;
    } else if (err.error?.message) {
      message = err.error.message;
    }

    console.error('Auth error:', err);
    return throwError(() => new Error(message));
  }
}