import { inject, Injectable } from '@angular/core';

import { BehaviorSubject, map, Observable } from 'rxjs';

import { Chart } from '@maps/interfaces/Chart.interface';
import { CentersMaps } from '@maps/interfaces/CentersMaps.interface';
import { MapParams } from '@maps/interfaces/MapParams.interface';
import { MapHttpService } from './map-http.service';

@Injectable({
  providedIn: 'root',
})
export class MapService {
  private maphttp: MapHttpService = inject(MapHttpService);

  public maps$: BehaviorSubject<Chart | null> =
    new BehaviorSubject<Chart | null>(null);

  //CentersMaps
  private centersMapsData = new BehaviorSubject<CentersMaps[]>([]);
  centersMaps$ = this.centersMapsData.asObservable();

  constructor() {}

  formMapLoad(params: MapParams) {
    return this.maphttp
      .getFormMap(params)
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
}
