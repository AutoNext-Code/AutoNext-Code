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
      catchError((error) => throwError(() => error))
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
}
