import { inject, Injectable } from '@angular/core';
import { DashboardHttpService } from './dashboard-http.service';
import { catchError, Observable, throwError } from 'rxjs';
import { DashboardDTO } from '../models/dashboard.model';
import { DashboardExportRequest } from '../interfaces/graphics-request.interface';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private dashboardHttp = inject(DashboardHttpService);

  constructor() {}

  getDashboardData(
    month: number | null,
    year: number
  ): Observable<DashboardDTO> {
    return this.dashboardHttp.getDashboard(month, year).pipe(
      catchError((error) => {
        console.error('Error al cargar el dashboard:', error);
        return throwError(
          () => new Error('No se pudo cargar el resumen del dashboard')
        );
      })
    );
  }

  exportDashboardPdf(payload: DashboardExportRequest): void {
    this.dashboardHttp
      .exportDashboardPdf(payload)
      .pipe(
        catchError((err) => {
          console.error('Error al descargar el PDF del dashboard:', err);
          return throwError(() => new Error('No se pudo generar el PDF.'));
        })
      )
      .subscribe((blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        window.open(url, '_blank');
      });
  }
}
