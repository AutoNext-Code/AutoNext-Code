import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import {
  CONFIRM_EMAIL_ENDPOINT,
  FORGET_PASSWORD,
  LOGIN_ENDPOINT,
  REGISTER_ENDPOINT,
  RESET_PASSWORD,
} from '../../config';

@Injectable({
  providedIn: 'root',
})
export class AuthHttpService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  login(email: string, password: string): Observable<string> {
    const body = { email, password };
    return this.http.post(LOGIN_ENDPOINT, body, { responseType: 'text' });
  }

  register(
    email: string,
    name: string,
    surname: string,
    password: string,
    carPlate: string
  ): Observable<string> {
    const body = { email, name, surname, password, carPlate };
    return this.http.post(REGISTER_ENDPOINT, body, { responseType: 'text' });
  }

  confirmEmail(token: string): Observable<string> {
    return this.http.put(CONFIRM_EMAIL_ENDPOINT, token, {
      responseType: 'text',
    });
  }

  requestPasswordReset(email: string): Observable<string> {
    return this.http.post(FORGET_PASSWORD, { email }, { responseType: 'text' });
  }

  requestNewPassword(password: string): Observable<string> {
    return this.http.post(RESET_PASSWORD, { password }, { responseType: 'text' });
  }
}
