import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { CarDto } from '../interfaces/car.interface';
import { Observable } from 'rxjs';
import { CARS_USER_ENDPOINT } from '../../config';

@Injectable({providedIn: 'root'})
export class CarHttpService {

  private http: HttpClient = inject(HttpClient);

  constructor() {}

  getCarsByUser(): Observable<CarDto[]> {
    return this.http.get<CarDto[]>(CARS_USER_ENDPOINT);
  }

  createCar(car: CarDto): Observable<string> {
    return this.http.post<string>(CARS_USER_ENDPOINT, car, {
      responseType: 'text' as 'json'
    });
  }

  updateCar(car: CarDto): Observable<string> {
    return this.http.put<string>(CARS_USER_ENDPOINT, car, {
      responseType: 'text' as 'json'
    });
  }

  deleteCar(id: number): Observable<string> {
    return this.http.delete<string>(`${CARS_USER_ENDPOINT}/${id}`, {
      responseType: 'text' as 'json'
    });
  }

}
