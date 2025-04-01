import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BookingDTO } from '../interfaces/booking.interface';
import { BOOKINGS_ENDPOINT } from '../../config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BookingHttpService {

  private http: HttpClient = inject(HttpClient);

  constructor() {}

}
