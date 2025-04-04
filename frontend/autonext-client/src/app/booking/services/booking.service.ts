import { inject, Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import {
  BehaviorSubject,
  Observable,
  catchError,
  map,
  tap,
  throwError,
} from 'rxjs';

import { BookingHttpService } from './booking-http.service';
import { BookingDTO } from '@booking/interfaces/bookingDTO.interface';
import { BookingParams } from '@booking/interfaces/booking-params.interface';
import { SpaceData } from '@booking/interfaces/spaceData.interface';

@Injectable({
  providedIn: 'root',
})
export class BookingService {
  private bookingHttp = inject(BookingHttpService);

  private bookingListSubject = new BehaviorSubject<BookingDTO[]>([]);
  public bookingList$ = this.bookingListSubject.asObservable();

  private totalSubject = new BehaviorSubject<number>(0);
  public total$ = this.totalSubject.asObservable();

  private lastParams: BookingParams = {};

  getBookingsByUser(
    params: BookingParams
  ): Observable<{ content: BookingDTO[]; totalElements: number }> {
    this.lastParams = params; // guardamos los últimos filtros para recargar
    return this.bookingHttp.getBookingsByUser(params).pipe(
      tap((res) => {
        this.bookingListSubject.next(res.content);
        this.totalSubject.next(res.totalElements);
      }),
      catchError((err: HttpErrorResponse) => {
        console.error('Error al obtener reservas:', err);
        return throwError(
          () => new Error('No se pudieron obtener las reservas')
        );
      })
    );
  }

  getUserCars(): Observable<{ id: number; name: string }[]> {
    return this.bookingHttp.getUserCars();
  }

  getWorkCenters(): Observable<{ id: number; name: string }[]> {
    return this.bookingHttp.getWorkCenters().pipe(
      tap((res) => console.log('[WorkCenters raw]', res)),
      map((response) =>
        Object.entries(response).map(([id, name]) => ({
          id: +id,
          name: name as string,
        }))
      )
    );
  }

  cancelBooking(id: number): Observable<void> {
    return this.bookingHttp.cancelBooking(id).pipe(
      tap(() => this.refreshBookings()),
      catchError((err: HttpErrorResponse) => {
        console.error('Error al cancelar la reserva:', err);
        return throwError(() => new Error('No se pudo cancelar la reserva'));
      })
    );
  }

  updateConfirmationStatus(id: number, status: string): Observable<void> {
    return this.bookingHttp.updateConfirmationStatus(id, status).pipe(
      tap(() => this.refreshBookings()),
      catchError((err: HttpErrorResponse) => {
        console.error('Error al confirmar la reserva:', err);
        return throwError(() => new Error('No se pudo confirmar la reserva'));
      })
    );
  }

  private refreshBookings() {
    this.getBookingsByUser(this.lastParams).subscribe();
  }

  postBooking(params: SpaceData): void {
    this.bookingHttp.postBooking(params).subscribe({
      next: (book: any) => {
        console.log('Reserva creada con éxito:', book);
      },
      error: (err: any) => {
        console.error('Error al reservar:', err);
      },
    });
  }
}
