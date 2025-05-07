import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { AdminHttpService } from './admin-http.service';
import { UserForAdmin } from '@admin/interfaces/user-for-admin.interface';
import { JobPosition } from '@admin/enums/jobPosition.enum';

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

  updateJobPosition(id: number, jobPosition: string): Observable<string> {
    return this.adminHttp.updateJobPosition(id, jobPosition).pipe(
      catchError((error) => throwError(() => error))
    );
  }

  updateWorkCenter(id: number, workCenter: number): Observable<string> {
    return this.adminHttp.updateWorkCenter(id, workCenter).pipe(
      catchError((error) => throwError(() => error))
    );
  }
}
