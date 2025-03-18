import { inject, Injectable } from '@angular/core';
import { AuthHttpService } from './auth-http.service';
import { catchError, Observable, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private authHttp: AuthHttpService = inject(AuthHttpService)

  public token: string;

  constructor() {
    this.token = "";
   }

   public login(email: string, password: string): Observable<string> {
    return this.authHttp.login(email, password).pipe(
      tap((token: string) => {
        this.setToken(token);
      }),
      catchError((err) => {
        return throwError(() => new Error('Login failed', err));
      })
    );
  }

  private setToken(token: string): void {
    // TODO: SE DEBERIA GUARDAR EN EL STORAGE DEL NAVEGADOR
    this.token = token;
  }

  // TODO: ROLE Y OTROS DATOS DEL TOKEN POR HACER.
}
