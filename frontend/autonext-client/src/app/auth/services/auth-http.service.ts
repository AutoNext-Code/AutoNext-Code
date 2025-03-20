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

  register(name:string, surname:string, email: string, password:string, carPlate: string, ): Observable<string> {
    const body = { name, surname, email, password, carPlate };
    return this.http.post<string>(REGISTER_ENDPOINT, body);
  }

  confirmEmail(token: string): Observable<string> {
    return this.http.put<string>(`${REGISTER_ENDPOINT}/${token}`, {});
  }

}