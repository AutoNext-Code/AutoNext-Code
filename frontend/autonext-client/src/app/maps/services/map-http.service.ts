import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";

import { Chart } from "@maps/interfaces/Chart.interface";
import { MapParams } from "@maps/interfaces/MapParams.interface";
import { CentersMaps } from "@maps/interfaces/CentersMaps.interface";

import { AuthService } from "@auth/services/auth.service";

import { BOOKINGS_USER_CHECK, CENTERS_LEVELS, MAP_ENDPOINT } from "../../config";

import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root',
})
export class MapHttpService{
  private http: HttpClient = inject(HttpClient);
  private authService: AuthService = inject(AuthService);

  constructor() {}


  getMap( mapId: number): Observable<Chart>{
    return this.http.get<any>(`${MAP_ENDPOINT}/${mapId}`);
  }

  getCanUserBook(date: string, startTime: string, endTime: string): Observable<boolean>{
    let params = new HttpParams();


    if(date && startTime && endTime){

      params = params.set('date', date);
      params = params.set('startHour', startTime+":00");
      params = params.set('endHour', endTime+":00");
    }
    console.log(params) ;

    return this.http.post<boolean>(`${BOOKINGS_USER_CHECK}`, {params});
  }

  getFormMap(mapParams: MapParams): Observable<Chart>{
    let params = new HttpParams();

    if(mapParams){

      params = params.set('date', mapParams.date);
      params = params.set('startTime', mapParams.startTime);
      params = params.set('endTime', mapParams.endTime);
    }

    return this.http.get<any>(`${MAP_ENDPOINT}/${mapParams.mapId}`, {params});
  }

  getCentersLevels(): Observable<CentersMaps[]>{
    return this.http.get<any>(`${CENTERS_LEVELS}`);
  }
}
