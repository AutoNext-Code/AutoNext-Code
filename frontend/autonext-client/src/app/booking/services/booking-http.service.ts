import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import {
  BOOKINGS_ENDPOINT,
  BOOKING_CANCEL_ENDPOINT,
  BOOKING_CONFIRMATION_ENDPOINT,
  BOOKINGS_LIST_ENDPOINT,
  CARS_USER_ENDPOINT,
  CENTER_NAME_ENDPOINT,
} from '../../config';

import { BookingDTO } from '@booking/interfaces/bookingDTO.interface';
import { BookingParams } from '@booking/interfaces/booking-params.interface';
import { SpaceData } from '@booking/interfaces/spaceData.interface';

@Injectable({
  providedIn: 'root',
})
export class BookingHttpService {
  private http = inject(HttpClient);

  getBookingsByUser(
    params: BookingParams
  ): Observable<{ content: BookingDTO[]; totalElements: number }> {
    const httpParams = this.buildHttpParams(params);

    return this.http.get<{ content: BookingDTO[]; totalElements: number }>(
      BOOKINGS_LIST_ENDPOINT,
      {
        params: httpParams,
      }
    );
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

  getUserCars(): Observable<{ id: number; name: string }[]> {
    return this.http.get<{ id: number; name: string }[]>(CARS_USER_ENDPOINT);
  }

  getWorkCenters(): Observable<Record<string, string>> {
    return this.http.get<Record<string, string>>(CENTER_NAME_ENDPOINT);
  }

  cancelBooking(id: number): Observable<void> {
    return this.http.put<void>(BOOKING_CANCEL_ENDPOINT(id), {});
  }

  updateConfirmationStatus(
    id: number,
    confirmationStatus: string
  ): Observable<string> {
    return this.http.put(
      BOOKING_CONFIRMATION_ENDPOINT(id),
      {},
      {
        params: { confirmationStatus },
        responseType: 'text' as const
      }
    );
  }
  
  
  postBooking(params: SpaceData): Observable<string> {
    console.log(params);
    const body = {
      carId: params.carId,
      date: params.date,
      parkingSpaceId: params.parkingSpaceId,
      endTime: params.endTime,
      startTime: params.startTime,
    };
    return this.http.post(BOOKINGS_ENDPOINT, body, { responseType: 'text' });
  }
}
