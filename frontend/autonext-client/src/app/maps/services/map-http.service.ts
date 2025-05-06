import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";

import { Chart } from "@maps/interfaces/Chart.interface";
import { MapParams } from "@maps/interfaces/MapParams.interface";
import { CentersMaps } from "@maps/interfaces/CentersMaps.interface";

import { AuthService } from "@auth/services/auth.service";

import { BOOKINGS_USER_CHECK, CENTERS_LEVELS, MAP_ENDPOINT } from "../../config";

import { Observable } from "rxjs";
import { CanBookResponse } from "@maps/interfaces/CanBookResponse";

@Injectable({
  providedIn: 'root',
})
export class MapHttpService{
  private http: HttpClient = inject(HttpClient);

  constructor() {}


  getMap( mapId: number): Observable<Chart>{
    return this.http.get<any>(`${MAP_ENDPOINT}/${mapId}`);
  }

  getCanUserBook(date: string, startTime: string, endTime: string): Observable<CanBookResponse>{
    const body = {
      date: date,
      startTime: startTime+":00",
      endTime: endTime+":00",
    }

    return this.http.post<CanBookResponse>(`${BOOKINGS_USER_CHECK}`, body);
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

  getFormMapById(id: number): Observable<Chart>{
    return this.http.get<any>(`${MAP_ENDPOINT}/${id}`);
  }

  getCentersLevels(): Observable<CentersMaps[]>{
    return this.http.get<any>(`${CENTERS_LEVELS}`);
  }
}
