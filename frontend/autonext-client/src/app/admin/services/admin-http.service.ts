import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { UserForAdmin } from '@admin/interfaces/user-for-admin.interface';
import {
  CONFIG_PARKING_LIMIT,
  CONFIG_UPDATE_PARKING_LIMIT,
  GET_USERS,
  TOGGLE_ROLE,
  UPDATE_JOB_POSITION,
  UPDATE_SPACE_STATE,
  UPDATE_WORK_CENTER,
} from '../../config';

@Injectable({
  providedIn: 'root',
})
export class AdminHttpService {
  private http = inject(HttpClient);

  constructor() {}

  getUsers(email?: string): Observable<UserForAdmin[]> {
    const url = email ? `${GET_USERS}?email=${email}` : GET_USERS;

    return this.http
      .get<UserForAdmin | UserForAdmin[]>(url)
      .pipe(
        map((response) => (Array.isArray(response) ? response : [response]))
      );
  }

  toggleAdminRole(id: number): Observable<string> {
    return this.http.put(TOGGLE_ROLE(id), {}, { responseType: 'text' });
  }  
  
  updateJobPosition(id: number, jobPosition: string): Observable<string> {
    return this.http.put(`${UPDATE_JOB_POSITION(id)}?jobPosition=${jobPosition}`, {}, { responseType: 'text' });
}

  updateWorkCenter(id: number, workCenter: number): Observable<string> {
    return this.http.put(`${UPDATE_WORK_CENTER(id)}?workCenterId=${workCenter}`, {}, { responseType: 'text' });
  }  

  updateSpaceState(id: number): Observable<string> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.put(
      UPDATE_SPACE_STATE,
      {},
      { params, responseType: 'text' }
    );
  }
  

  getConfigParkingLimit(): Observable<number> {
    return this.http.get<number>(CONFIG_PARKING_LIMIT);
  }

  updateConfigParkingLimit(limit: number): Observable<string> {
    const params = new HttpParams().set('parkingLimit', limit.toString());
    return this.http.put<string>(CONFIG_UPDATE_PARKING_LIMIT, {}, { params,  responseType: 'text' as 'json' });
  }

}
