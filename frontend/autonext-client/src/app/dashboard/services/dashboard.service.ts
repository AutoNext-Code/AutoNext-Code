import { inject, Injectable } from '@angular/core';
import { DashboardHttpService } from './dashboard-http.service';
import { catchError, Observable, throwError } from 'rxjs';
import { DashboardDTO } from '../models/dashboard.model';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private dashboardHttp = inject(DashboardHttpService);

  constructor() {}

  getDashboardData(month: number, year: number): Observable<DashboardDTO> {
    return this.dashboardHttp.getDashboard(month, year).pipe(
      catchError((error) => {
        console.error('Error al cargar el dashboard:', error);
        return throwError(
          () => new Error('No se pudo cargar el resumen del dashboard')
        );
      })
    );
  }
}