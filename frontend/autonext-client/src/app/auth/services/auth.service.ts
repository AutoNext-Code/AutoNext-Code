import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { getVarSessionStorage, updateSessionStorage, clearStorage } from '../../../utils/storageUtils';
import { jwtDecode } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private tokenSubject = new BehaviorSubject<string | null>(getVarSessionStorage('token') || null);
  public token$ = this.tokenSubject.asObservable();

  private registerSubject = new BehaviorSubject<string | null>(null);
  public register$ = this.registerSubject.asObservable();

  private confirmEmailSubject = new BehaviorSubject<string | null>(null);
  public confirmEmail$ = this.confirmEmailSubject.asObservable();

  private role: string | null = null;
  private name: string | null = null;
  private id: number | null = null;

  constructor() {
    if (this.tokenSubject.value) {
      this.decodeToken();
    }
  }


  // Token y sesión
  public setToken(token: string): void {
    this.tokenSubject.next(token);
    updateSessionStorage('token', token);
    this.decodeToken();
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

  public getId(): number | null {
    return this.id;
  }

  public logout(): void {
    clearStorage();
    this.tokenSubject.next(null);
    this.role = null;
    this.name = null;
    this.id = null;
  }


  // Campos auxiliares para mensajes (registro, email, etc.)
  public setRegister(message: string): void {
    this.registerSubject.next(message);
  }

  public setConfirmEmail(message: string): void {
    this.confirmEmailSubject.next(message);
  }


  // Decodificación del token
  private decodeToken(): void {
    const token = this.tokenSubject.value;

    if (!token) {
      this.role = null;
      this.name = null;
      this.id = null;
      return;
    }

    try {
      const decoded: any = jwtDecode(token);
      this.role = decoded?.role || null;
      this.name = decoded?.name || null;
      this.id = decoded?.id || null;
    } catch (error) {
      console.error('Error decoding token:', error);
      this.role = null;
      this.name = null;
      this.id = null;
    }
  }

  public isUserPenalized(): boolean {
    return this.role == "Penalized";
  }
}
