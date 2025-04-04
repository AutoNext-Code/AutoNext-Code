import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable, OnInit } from "@angular/core";
import { Chart } from "@maps/interfaces/Chart.interface";
import { Observable } from "rxjs";
import { CENTERS_LEVELS, MAP_ENDPOINT } from "../../config";
import { CentersMaps } from "@maps/interfaces/CentersMaps.interface";
import { MapParams } from "@maps/interfaces/MapParams.interface";

@Injectable({
  providedIn: 'root',
})
export class MapHttpService{
  private http: HttpClient = inject(HttpClient);

  constructor() {}


  getMap( mapId: number): Observable<Chart>{
    return this.http.get<any>(`${MAP_ENDPOINT}/${mapId}`);
  }

  getFormMap(mapParams: MapParams): Observable<Chart>{
    let params = new HttpParams();

    if(mapParams){

      params = params.set('date', mapParams.date);

      if(mapParams.plugtype != 1){
        params = params.set('plugtype', mapParams.plugtype.toString());
        console.log(mapParams.plugtype.toString());
      }

      params = params.set('startTime', mapParams.startTime);
      params = params.set('endTime', mapParams.endTime);
    }

    return this.http.get<any>(`${MAP_ENDPOINT}/${mapParams.mapId}`, {params});
  }

  getCentersLevels(): Observable<CentersMaps[]>{
    return this.http.get<any>(`${CENTERS_LEVELS}`);
  }
}
