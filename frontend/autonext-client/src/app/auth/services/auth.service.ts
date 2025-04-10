import { inject, Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { AuthHttpService } from './auth-http.service';
import { clearStorage, getVarSessionStorage, updateSessionStorage } from '../../../utils/storageUtils';

import { jwtDecode } from 'jwt-decode';

import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private tokenSubject: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(getVarSessionStorage('token') || null);
  private registerSub:  BehaviorSubject<string | null> = new BehaviorSubject<string | null>("");
  private confirmEmailSubject: BehaviorSubject<string | null> = new BehaviorSubject<string | null>("");

  private authHttp: AuthHttpService = inject(AuthHttpService);

  public token$ = this.tokenSubject.asObservable();
  public register$ = this.registerSub.asObservable();
  public confirmEmail$ = this.confirmEmailSubject.asObservable();

  public role: string | null = null;
  public name: string | null = null;

  constructor() {
    if (this.tokenSubject.value) {
      this.decodeToken();
    }
  }

  public login(email: string, password: string): Observable<string> {
    return this.authHttp.login(email, password).pipe(
      tap((token: string) => {
        this.setToken(token);
      }),
      catchError((err: HttpErrorResponse) => {
        let errorMessage = 'Error desconocido';

        if (err.status === 401) {
          errorMessage = 'Credenciales incorrectas. Verifica tu usuario y contraseña.';
        } else if (err.error && typeof err.error === 'string') {
          errorMessage = err.error;
        } else if (err.error?.message) {
          errorMessage = err.error.message;
        }

        return throwError(() => new Error(errorMessage));
      })
    );
  }

  public register(name:string, surname:string, email: string, password:string, carPlate: string): Observable<string> {

    return this.authHttp.register(email, name, surname,  password, carPlate).pipe(
      tap((register: string) => {
        this.setRegister(register)
      }),
      catchError((err: HttpErrorResponse) => {
        console.log(err)
        return throwError(() => new Error('Register failed: ' + err.message));
      })
    );
  }

  public confirmEmail(tokenEmail:string): Observable<string> {

    return this.authHttp.confirmEmail(tokenEmail).pipe(
      tap((message: string) => {
        this.setconfirmEmail(message)
      }),
      catchError((err: HttpErrorResponse) => {
        console.log(err)
        return throwError(() => new Error('confirm email failed: ' + err.message));
      })
    );
  }


  private setToken(token: string): void {
    this.tokenSubject.next(token);
    updateSessionStorage('token', token);
    this.decodeToken();
  }

  private setRegister(register: string): void {
    this.registerSub.next(register) ;
  }


  private setconfirmEmail(message: string): void {
    this.confirmEmailSubject.next(message) ;
  }

  private decodeToken(): void {
    const token = this.tokenSubject.value;

    if (!token) {
      this.role = null;
      this.name = null;
      return;
    }

    try {
      const decodedToken: any = jwtDecode(token);
      this.role = decodedToken?.role || null;
      this.name = decodedToken?.name || null;
    } catch (error) {
      console.error('Error decoding token:', error);
      this.role = null;
      this.name = null;
    }
  }

  public getToken(): string | null {
    return this.tokenSubject.value;
  }

  public getRole(): string | null {
    return this.role;
  }

  public getName(): string | null {
    return this.name;
  }

  public logout(): void {
    clearStorage();
    this.tokenSubject.next(null);
    this.role = null;
    this.name = null;
  }
}
