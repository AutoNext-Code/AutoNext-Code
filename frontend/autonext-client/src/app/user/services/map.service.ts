import { Chart } from '../../maps/interfaces/Chart.interface';
import { inject, Injectable } from "@angular/core";
import { BehaviorSubject, delay, map, Observable, of, timeout } from "rxjs";
import { MapHttpService } from './map-http.service';
import { CentersMaps } from '@maps/interfaces/CentersMaps.interface';
import { MapParams } from '@maps/interfaces/MapParams.interface';



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



  formMapLoad(params:MapParams){
 
    return this.maphttp.getFormMap(params)
    .pipe(
      map(response => response as Chart)
    );
 
  }
 



  centersLevelsLoad(){
    this.maphttp.getCentersLevels()
    .pipe(
      map(response => response as CentersMaps[])
    )
    .subscribe({
      next: (data) => {
        this.centersMapsData.next(data);
      },
      error: (error) => {
        console.error('Error al cargar los datos:', error);
      },
      complete: () => {
        console.log('Carga de datos completada.');
      }
    });

  }








}
