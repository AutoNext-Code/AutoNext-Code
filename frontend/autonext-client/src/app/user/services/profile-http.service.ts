import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { UserDto } from '@user/interfaces/user.interface';
import { DATA_PROFILE_ENDPOINT } from '../../config';

@Injectable({
  providedIn: 'root',
})
export class ProfileHttpService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  getDataProfile(headers: HttpHeaders): Observable<UserDto> {
    return this.http.get<UserDto>(DATA_PROFILE_ENDPOINT, { headers });
  }

}
