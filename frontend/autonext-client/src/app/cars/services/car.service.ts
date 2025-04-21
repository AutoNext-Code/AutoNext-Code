import { inject, Injectable } from '@angular/core';
import { CarHttpService } from './car-http.service';
import { AuthService } from '@auth/services/auth.service';
import { CarDto } from '../interfaces/car.interface';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CarService {
  private carHttp: CarHttpService = inject(CarHttpService);

  constructor() {}

  getCarsByUser(): Observable<CarDto[]> {
    return this.carHttp.getCarsByUser().pipe(
      catchError((error) => {
        console.error('Error al obtener coches del usuario:', error);
        return throwError(() => error);
      })
    );
  }

  createCar(car: CarDto): Observable<string> {
    return this.carHttp.createCar(car).pipe(
      catchError((error) => {
        console.error('Error al crear el coche:', error);
        return throwError(() => error);
      })
    );
  }


  updateCar(car: CarDto): Observable<string> {
    return this.carHttp.updateCar(car).pipe(
      catchError((error) => {
        console.error('Error al actualizar el coche:', error);
        return throwError(() => error);
      })
    );
  }

  deleteCar(id: number): Observable<string> {
    return this.carHttp.deleteCar(id).pipe(
      catchError((error) => {
        console.error('Error al eliminar el coche:', error);
        return throwError(() => error);
      })
    );
  }
}
