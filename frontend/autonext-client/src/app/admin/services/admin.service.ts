import { inject, Injectable } from '@angular/core';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { AdminHttpService } from './admin-http.service';
import { UserForAdmin } from '@admin/interfaces/user-for-admin.interface';
import { BookingDTO } from '@booking/interfaces/bookingDTO.interface';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private adminHttp = inject(AdminHttpService);

  getUsers(email?: string): Observable<UserForAdmin[]> {
    return this.adminHttp.getUsers(email).pipe(
      catchError((error) => {
        console.error('Error al cargar usuarios:', error);
        return throwError(
          () =>
            new Error(
              'No se pudo cargar la lista de usuarios. Intenta más tarde.'
            )
        );
      })
    );
  }

  toggleAdminRole(id: number): Observable<string> {
    return this.adminHttp.toggleAdminRole(id).pipe(
      catchError((error) => {
        console.error('Error al cambiar el rol del usuario:', error);
        return throwError(
          () => new Error('No se pudo actualizar el rol del usuario.')
        );
      })
    );
  }

  updateSpaceState(id: number, blocked: boolean): Observable<string> {
    return this.adminHttp.updateSpaceState(id, blocked).pipe(
      catchError((error) => {
        console.error('Error al actualizar el estado del espacio:', error);
        return throwError(() => new Error('No se pudo actualizar el estado del espacio.'));
      })
    );
  }
  
/* TODO Mirar si funciona, si no cambiarlo */
  getAdminBooking(params: { id: number; page?: number }): Observable<{ content: BookingDTO[]; totalElements: number }> {
    return this.adminHttp.getAdminBooking(params).pipe(
      catchError((error) => {
        console.error('Error al obtener reservas por espacio:', error);
        return throwError(() => new Error('No se pudieron obtener las reservas por espacio'));
      })
    );
  }
}
