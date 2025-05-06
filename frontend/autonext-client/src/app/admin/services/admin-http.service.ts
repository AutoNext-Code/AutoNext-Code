import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { UserForAdmin } from '@admin/interfaces/user-for-admin.interface';
import {
  ADMIN_BOOKING,
  GET_USERS,
  TOGGLE_ROLE,
  UPDATE_JOB_POSITION,
  UPDATE_SPACE_STATE,
  UPDATE_WORK_CENTER,
} from '../../config';
import { BookingDTO } from '@booking/interfaces/bookingDTO.interface';

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

  updateJobPosition(id: number): Observable<string> {
    return this.http.put(UPDATE_JOB_POSITION(id), {}, { responseType: 'text' });
  }

  updateWorkCenter(id: number): Observable<string> {
    return this.http.put(UPDATE_WORK_CENTER(id), {}, { responseType: 'text' });
  }

  updateSpaceState(id: number, blocked: boolean): Observable<string> {
    const params = new HttpParams()
      .set('id', id.toString())
      .set('blocked', blocked.toString());
    return this.http.put(
      UPDATE_SPACE_STATE,
      {},
      { params, responseType: 'text' }
    );
  }

  getAdminBooking(params: {
    id: number;
    page?: number;
  }): Observable<{ content: BookingDTO[]; totalElements: number }> {
    const httpParams = new HttpParams().set(
      'page',
      (params.page ?? 0).toString()
    );

    return this.http.get<{ content: BookingDTO[]; totalElements: number }>(
      ADMIN_BOOKING(params.id),
      { params: httpParams }
    );
  }
}
