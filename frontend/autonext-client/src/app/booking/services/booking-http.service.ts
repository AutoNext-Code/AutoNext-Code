import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { BOOKINGS_LIST_ENDPOINT } from '../../config';
import { BookingDTO } from '@booking/interfaces/bookingDTO.interface';
import { BookingParams } from '@booking/interfaces/booking-params.interface';


@Injectable({
  providedIn: 'root'
})
export class BookingHttpService {
  private http = inject(HttpClient);

  getBookingsByUser(params: BookingParams): Observable<{ content: BookingDTO[]; totalElements: number }> {
    const httpParams = this.buildHttpParams(params);

    return this.http.get<{ content: BookingDTO[]; totalElements: number }>(BOOKINGS_LIST_ENDPOINT, {
      params: httpParams,
    });
  }

  private buildHttpParams(params: BookingParams): HttpParams {
    let httpParams = new HttpParams();

    Object.entries(params).forEach(([key, value]) => {
      if (value !== undefined && value !== null) {
        httpParams = httpParams.set(key, value);
      }
    });

    return httpParams;
  }
}
