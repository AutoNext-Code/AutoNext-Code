import { inject, Injectable } from '@angular/core';
import { PasswordHttpService } from './password-http.service';
import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { AuthService } from '@auth/services/auth.service';
import { catchError, Observable, tap, throwError } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class PasswordService {

  private authValidationHttp: PasswordHttpService = inject(PasswordHttpService); 
  private authService: AuthService = inject(AuthService); 
  
  constructor() {}

  

  validatePasswords(oldPassword: string, newPassword: string): Observable<string> {

    const token = this.authService.getToken();

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });


    return this.authValidationHttp.savePasswordChange(oldPassword, newPassword, headers)
    .pipe(
      tap((message: string) => {
        console.log('Mensaje del servidor:', message);
      }),
      catchError((err: HttpErrorResponse) => {
        console.error('Error en la petición:', err);
        return throwError(() => new Error('Error al enviar la petición: ' + err.message));
      })
    ) ;

  }

}
