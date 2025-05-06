import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';

import { BookingList } from '@maps/interfaces/bookingList.interface';
import { SPACE_BOOKINGS } from '../../config';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class BookingListHttpService {

    private http: HttpClient = inject(HttpClient);

    getAllBookingBySpace(id: number,page: number, headers: HttpHeaders): Observable<BookingList> {
        return this.http.get<BookingList>((SPACE_BOOKINGS(id)+"?page="+page), {headers} )
    }
    
}