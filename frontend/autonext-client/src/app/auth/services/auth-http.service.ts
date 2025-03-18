import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { LOGIN_ENDPOINT } from '../../config';

@Injectable({
  providedIn: 'root',
})
export class AuthHttpService {

  private http: HttpClient = inject(HttpClient)

  constructor() { }

  login(email: string, password: string): Observable<string> {
    const body = { email, password };

    return this.http.post<any>(LOGIN_ENDPOINT, body);
  }


}
