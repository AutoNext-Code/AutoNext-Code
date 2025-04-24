import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { DASHBOARD, DASHBOARD_PDF } from '../../config';
import { DashboardDTO } from '../models/dashboard.model';
import { Observable } from 'rxjs';
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

  exportDashboardPdf(payload: DashboardExportRequest): Observable<Blob> {
    return this.http.post(DASHBOARD_PDF, payload, {
      responseType: 'blob',
    });
  }
}
