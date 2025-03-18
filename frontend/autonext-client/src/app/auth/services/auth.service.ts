import { inject, Injectable } from '@angular/core';
import { AuthHttpService } from './auth-http.service';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { clearStorage, getVarSessionStorage, updateSessionStorage } from '../../../utils/storageUtils';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private authHttp: AuthHttpService = inject(AuthHttpService);
  public token: string | null;
  public role: string | null;
  public name: string | null;

  constructor() {
    this.token = getVarSessionStorage("token") || null;
    this.role = null;
    this.name = null;

    if (this.token) {
      this.decodeToken();
    }
  }

  public login(email: string, password: string): Observable<string> {
    return this.authHttp.login(email, password).pipe(
      tap((token: string) => {
        this.setToken(token);
      }),
      catchError((err) => {
        return throwError(() => new Error('Login failed: ' + err.message));
      })
    );
  }

  private setToken(token: string): void {
    this.token = token;
    updateSessionStorage("token", token);
    this.decodeToken();
  }

  private decodeToken(): void {
    if (!this.token) {
      this.role = null;
      return;
    }

    try {
      const decodedToken: any = jwtDecode(this.token);
      this.role = decodedToken?.role || null;
      this.name = decodedToken?.name || null;
    } catch (error) {
      console.error("Error decoding token:", error);
      this.role = null;
      this.name = null;
    }
  }

  public getRole(): string | null {
    return this.role;
  }

  public getName(): string | null {
    return this.name;
  }

  public logout(): void {
    clearStorage();
    this.token = null;
    this.role = null;
    this.name = null;
  }
}
