import { HttpClient, HttpParams } from "@angular/common/http";
import { inject, Injectable, OnInit } from "@angular/core";
import { Chart } from "@maps/interfaces/Chart.interface";
import { Observable } from "rxjs";
import { CENTERS_LEVELS, MAP_ENDPOINT } from "../../config";
import { CentersMaps } from "@maps/interfaces/CentersMaps.interface";
import { PlugType } from '../../maps/enums/PlugType.enum';

@Injectable({
  providedIn: 'root',
})
export class MapHttpService{
  private http: HttpClient = inject(HttpClient);

  constructor() {}


  getMap( mapId: number): Observable<Chart>{
    return this.http.get<any>(`${MAP_ENDPOINT}/${mapId}`);
  }

  getFormMap(mapId:number, date:string, plugType:number, startTime:string, endTime:string): Observable<Chart>{
    let params = new HttpParams();

    if(date && plugType && mapId && startTime && endTime){

      params = params.set('date', date);

      if(plugType != 1){
        params = params.set('plugType', plugType.toString());
      }

      params = params.set('startTime', startTime);
      params = params.set('endTime', endTime);
    }

    return this.http.get<any>(`${MAP_ENDPOINT}/${mapId}`, {params});
  }

  getCentersLevels(): Observable<CentersMaps[]>{
    return this.http.get<any>(`${CENTERS_LEVELS}`);
  }
}
