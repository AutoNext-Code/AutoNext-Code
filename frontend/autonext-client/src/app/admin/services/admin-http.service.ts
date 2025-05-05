import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { UserForAdmin } from '@admin/interfaces/user-for-admin.interface';
import { GET_USERS, TOGGLE_ROLE } from '../../config';

@Injectable({
  providedIn: 'root',
})
export class AdminHttpService {
  private http = inject(HttpClient);

  constructor() {}

  getUsers(email?: string): Observable<UserForAdmin[]> {
    const url = email ? `${GET_USERS}?email=${email}` : GET_USERS;
  
    return this.http.get<UserForAdmin | UserForAdmin[]>(url).pipe(
      map((response) => Array.isArray(response) ? response : [response])
    );
  }
  
  toggleAdminRole(id: number): Observable<string> {
    return this.http.put(TOGGLE_ROLE(id), {}, { responseType: 'text' });
  }  
}
