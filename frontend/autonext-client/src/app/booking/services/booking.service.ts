import { inject, Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';

import { BookingHttpService } from './booking-http.service';
import { BookingDTO } from '@booking/interfaces/bookingDTO.interface';
import { BookingParams } from '@booking/interfaces/booking-params.interface';
import { SpaceData } from '@booking/interfaces/spaceData.interface';

@Injectable({
  providedIn: 'root'
})
export class BookingService {
  private bookingHttp = inject(BookingHttpService);

  private bookingListSubject = new BehaviorSubject<BookingDTO[]>([]);
  public bookingList$ = this.bookingListSubject.asObservable();

  private totalSubject = new BehaviorSubject<number>(0);
  public total$ = this.totalSubject.asObservable();

  private bookingSub:  BehaviorSubject<string | null> = new BehaviorSubject<string | null>("");

  getBookingsByUser(params: BookingParams): Observable<{ content: BookingDTO[]; totalElements: number }> {
    return this.bookingHttp.getBookingsByUser(params).pipe(
      tap((res) => {
        this.bookingListSubject.next(res.content);
        this.totalSubject.next(res.totalElements);
      }),
      catchError((err: HttpErrorResponse) => {
        console.error('Error al obtener reservas:', err);
        return throwError(() => new Error('No se pudieron obtener las reservas'));
      })
    );
  }

  postBooking(params: SpaceData): void {
    this.bookingHttp.postBooking(params).subscribe({ 
      next: (book: any) => {
        console.log("Reserva creada con éxito:", book);
      },
      error: (err: any) => {
        console.error("Error al reservar:", err);
        alert("Error al reservar: " + err.message);
      }
    });
  }

  private setBooking(booking: string): void {
    this.bookingSub.next(booking) ;
  }

}
