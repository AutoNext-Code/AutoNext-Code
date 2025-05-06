import { inject, Injectable } from '@angular/core';
import { BookingListHttpService } from './booking-list-http.service';
import { Observable } from 'rxjs';
import { BookingList } from '@maps/interfaces/bookingList.interface';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '@auth/services/auth.service';

@Injectable({providedIn: 'root'})
export class BookingListService {

    private bookingListHttpService: BookingListHttpService = inject(BookingListHttpService) ;
    private authService: AuthService = inject(AuthService) ;

    getAllBookingBySpace(id: number): Observable<BookingList> {

        const token = this.authService.getToken();

        const headers: HttpHeaders = new HttpHeaders({
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
        });

        return this.bookingListHttpService.getAllBookingBySpace(id, headers) ;
    }
    
}