import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { DASHBOARD } from '../../config';
import { DashboardDTO } from '../models/dashboard.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DashboardHttpService {
  private http = inject(HttpClient);

  constructor() {}

  getDashboard(month: number, year: number): Observable<DashboardDTO> {
    return this.http.get<DashboardDTO>(
      `${DASHBOARD}?month=${month}&year=${year}`
    );
  }
}