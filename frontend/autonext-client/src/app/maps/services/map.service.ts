import { Chart } from '../interfaces/Chart.interface';
import { inject, Injectable } from "@angular/core";
import { BehaviorSubject, delay, map, Observable, of, timeout } from "rxjs";
import { MapHttpService } from './map-http.service';
import { CentersMaps } from '@maps/interfaces/CentersMaps.interface';



@Injectable({
  providedIn: 'root'
})

export class MapService {

  private maphttp: MapHttpService = inject (MapHttpService);

  public maps$: BehaviorSubject<Chart | null> = new BehaviorSubject<Chart | null>(null);

  //CentersMaps
  private centersMapsData = new BehaviorSubject<CentersMaps[]>([]);
  centersMaps$ = this.centersMapsData.asObservable();

  constructor(){

  }

  mapLoad(mapId: number): Observable<Chart>{


    return this.maphttp.getMap(mapId)
    .pipe(
      map(response => response as Chart)
    );
  }

  centersLevelsLoad(){
    setTimeout(() => {
      const data: CentersMaps[] = [
        {
          centerName: "Madrid",
          parkingLevels: [
            { id: 1, name: "1" },
            { id: 2, name: "2" },
            { id: 3, name: "3" }
          ]
        },
        {
          centerName: "Málaga",
          parkingLevels: [
            { id: 4, name: "0" },
            { id: 5, name: "1" }
          ]
        }
      ];
      this.centersMapsData.next(data);
    }, 2000);
  }





}
