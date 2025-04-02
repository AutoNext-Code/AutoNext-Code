import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { CarDto } from '@user/interfaces/car.interface';
import { Observable } from 'rxjs';
import { CARS_USER_ENDPOINT } from '../../config';

@Injectable({providedIn: 'root'})
export class CarHttpService {

  private http: HttpClient = inject(HttpClient);

  constructor() {}

  getCarsByUser(headers: HttpHeaders): Observable<CarDto[]> {
    return this.http.get<CarDto[]>(CARS_USER_ENDPOINT, { headers });
  }
    
}