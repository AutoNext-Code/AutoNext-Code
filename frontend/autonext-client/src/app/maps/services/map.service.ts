import { inject, Injectable } from '@angular/core';

import { BehaviorSubject, map, Observable } from 'rxjs';

import { Chart } from '@maps/interfaces/Chart.interface';
import { CentersMaps } from '@maps/interfaces/CentersMaps.interface';
import { MapParams } from '@maps/interfaces/MapParams.interface';
import { MapHttpService } from './map-http.service';
import { CanBookResponse } from '@maps/interfaces/CanBookResponse';

@Injectable({
  providedIn: 'root',
})
export class MapService {
  private maphttp: MapHttpService = inject(MapHttpService);

  public maps$: BehaviorSubject<Chart | null> =
    new BehaviorSubject<Chart | null>(null);

  private centersMapsData = new BehaviorSubject<CentersMaps[]>([]);
  centersMaps$ = this.centersMapsData.asObservable();

  constructor() {}

  formMapLoad(params: MapParams) {
    return this.maphttp
      .getFormMap(params)
      .pipe(map((response) => response as Chart));
  }

  formMapLoadById(id: number) {
    return this.maphttp
      .getFormMapById(id)
      .pipe(map((response) => response as Chart));
  }

  formAdminMapById(id:number){
    return this.maphttp
    .getAdminMapById(id)
    .pipe(map((response) => response as Chart));
  }

  centersLevelsLoad() {
    this.maphttp
      .getCentersLevels()
      .pipe(map((response) => response as CentersMaps[]))
      .subscribe({
        next: (data) => {
          this.centersMapsData.next(data);
        },
        error: (error) => {
          console.error('Error al cargar los datos:', error);
        },
      });
  }

  checkUserCanBook(date: string, startTime: string, endTime: string): Observable<CanBookResponse> {

    return this.maphttp.getCanUserBook(date, startTime, endTime) ;

  }

}
