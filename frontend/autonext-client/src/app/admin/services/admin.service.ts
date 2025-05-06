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
      catchError((error) => throwError(() => error))
    );
  }

  toggleAdminRole(id: number): Observable<string> {
    return this.adminHttp.toggleAdminRole(id).pipe(
      catchError((error) => throwError(() => error))
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
}
