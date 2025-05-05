import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { PROFILE_EDIT_ENDPOINT } from '../../config';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EditProfileHttpService {

  private http: HttpClient = inject(HttpClient);

  constructor() { }

  editProfile(name: string, surname: string, email: string, headers: HttpHeaders): Observable<Object> {
    const body = { name, surname, email } ;
  
    return this.http.put(PROFILE_EDIT_ENDPOINT, body, { responseType: 'json', headers: headers }) ;
  }
}
