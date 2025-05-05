import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { PASSWORD_CHANGE_ENDPOINT } from '../../config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PasswordHttpService {

  private http: HttpClient = inject(HttpClient);

  constructor() {}

  savePasswordChange(oldPassword: string, newPassword: string, headers: HttpHeaders): Observable<string> {
    const body = { oldPassword: oldPassword, newPassword: newPassword } ;
 
    return this.http.patch(PASSWORD_CHANGE_ENDPOINT, body, { responseType: 'text', headers: headers }) ;
  }

}
