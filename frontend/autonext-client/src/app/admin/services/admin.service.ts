import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { AdminHttpService } from './admin-http.service';
import { UserForAdmin } from '@admin/interfaces/user-for-admin.interface';

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

  updateJobPosition(id: number): Observable<string> {
    return this.adminHttp.updateJobPosition(id).pipe(
      catchError((error) => {
        console.error('Error al actualizar el puesto de trabajo:', error);
        return throwError(
          () => new Error('No se pudo actualizar el puesto de trabajo.')
        );
      })
    );
  }

  updateWorkCenter(id: number): Observable<string> {
    return this.adminHttp.updateWorkCenter(id).pipe(
      catchError((error) => {
        console.error('Error al actualizar el centro de trabajo:', error);
        return throwError(
          () => new Error('No se pudo actualizar el centro de trabajo.')
        );
      })
    );
  }
}
