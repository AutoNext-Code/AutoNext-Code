import { HttpClient, HttpHeaders } from "@angular/common/http";
import { inject, Injectable, OnInit } from "@angular/core";
import { Chart } from "@maps/interfaces/Chart.interface";
import { Observable } from "rxjs";
import { MAP_ENDPOINT } from "../../config";
import { AuthService } from "@auth/services/auth.service";

@Injectable({
  providedIn: 'root',
})
export class MapHttpService implements OnInit{
  private http: HttpClient = inject(HttpClient);
  private auth: AuthService= inject(AuthService);

  constructor() {}

  ngOnInit(): void {
      this.updateToken();
  }


  updateToken(){
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.auth.getToken()}`);
    console.log(this.auth.getToken());
  }

  getMap(headers: HttpHeaders, mapId: number): Observable<Chart>{
    return this.http.get<any>(`${MAP_ENDPOINT}/${mapId}`, { headers })
  }
}
