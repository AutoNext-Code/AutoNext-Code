import { Chart } from '../interfaces/Chart.interface';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { MAP_ENDPOINT } from "../../config";
import { BehaviorSubject, map, Observable } from "rxjs";
import { AuthService } from '@auth/services/auth.service';



@Injectable({
  providedIn: 'root'
})

export class MapLoaderService {

  private http: HttpClient= inject(HttpClient);
  private auth: AuthService= inject(AuthService);

  public map$: BehaviorSubject<Chart | null> = new BehaviorSubject<Chart | null>(null);;

  constructor(){
  }

  mapLoad(mapId: String): Observable<Chart>{

    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.auth.getToken()}`);
    console.log(this.auth.getToken());

    return this.http.get<any>(`${MAP_ENDPOINT}/${mapId}`, { headers })
    .pipe(
      map(response => response as Chart)
    );
  }



}
