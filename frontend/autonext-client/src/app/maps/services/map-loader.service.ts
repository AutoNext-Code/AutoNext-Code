import { Chart } from '../interfaces/Chart.interface';
import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { MAP_ENDPOINT } from "../../config";
import { BehaviorSubject, map, Observable } from "rxjs";



@Injectable({
  providedIn: 'root'
})

export class MapLoaderService {

  private http: HttpClient= inject(HttpClient);

  public map$: BehaviorSubject<Chart | null> = new BehaviorSubject<Chart | null>(null);;

  constructor(){
  }

  mapLoad(mapId: String): Observable<Chart>{

    return this.http.get<any>(`${MAP_ENDPOINT}/${mapId}`)
    .pipe(
      map(response => response as Chart)
    );
  }



}
