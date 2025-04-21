import { inject, Injectable } from '@angular/core';
import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';

import { AuthService } from '@auth/services/auth.service';

import { EditProfileHttpService } from './edit-profile-http.service';

import { catchError, Observable, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EditProfileService {

  constructor() { }

  private authService: AuthService = inject(AuthService); 
  private editProfileHttpService: EditProfileHttpService = inject(EditProfileHttpService);  

  editProfile(name: string, surname: string, email: string): Observable<string> {

    const token = this.authService.getToken();

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });


    return this.editProfileHttpService.editProfile(name, surname, email, headers)
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
