import { HttpClient } from "@angular/common/http";
import { inject, Injectable, OnInit } from "@angular/core";
import { Chart } from "@maps/interfaces/Chart.interface";
import { Observable } from "rxjs";
import { MAP_ENDPOINT } from "../../config";

@Injectable({
  providedIn: 'root',
})
export class MapHttpService{
  private http: HttpClient = inject(HttpClient);

  constructor() {}


  getMap( mapId: number): Observable<Chart>{
    return this.http.get<any>(`${MAP_ENDPOINT}/${mapId}`);
  }
}
