import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { DASHBOARD, DASHBOARD_PDF, DASHBOARD_YEARS } from '../../config';
import { DashboardDTO } from '../models/dashboard.model';
import { map, Observable } from 'rxjs';
import { DashboardExportRequest } from '../interfaces/graphics-request.interface';

@Injectable({
  providedIn: 'root',
})
export class DashboardHttpService {
  private http = inject(HttpClient);

  constructor() {}

  getDashboard(month: number | null, year: number): Observable<DashboardDTO> {
    let params = new HttpParams().set('year', year.toString());

    if (month !== null) {
      params = params.set('month', month.toString());
    }
    return this.http.get<DashboardDTO>(DASHBOARD, { params });
  }

  exportDashboardPdf(
    payload: DashboardExportRequest
  ): Observable<Blob & { filename?: string }> {
    return this.http
      .post(DASHBOARD_PDF, payload, {
        responseType: 'blob',
        observe: 'response',
      })
      .pipe(
        map((response) => {
          const disposition = response.headers.get('Content-Disposition');
          const filename = disposition
            ? disposition.split('filename=')[1]?.replace(/"/g, '')
            : undefined;

          const blob = response.body as Blob & { filename?: string };
          blob.filename = filename;
          return blob;
        })
      );
  }

  getAvailableYears(): Observable<number[]> {
    return this.http.get<number[]>(DASHBOARD_YEARS);
  }
}
