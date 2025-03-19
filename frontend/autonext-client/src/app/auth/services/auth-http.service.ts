import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { LOGIN_ENDPOINT, REGISTER_ENDPOINT } from '../../config';

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

  register(email: string, name:string, surname:string, password:string): Observable<void> {
    const body = { email, name, surname, password };
    return this.http.post<void>(REGISTER_ENDPOINT, body);
  }
}