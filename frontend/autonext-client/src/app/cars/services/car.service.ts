import { inject, Injectable } from '@angular/core';
import { CarHttpService } from './car-http.service';
import { AuthService } from '@auth/services/auth.service';
import { CarDto } from '../interfaces/car.interface';
import { catchError, Observable, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CarService {
  private carHttp: CarHttpService = inject(CarHttpService);
  private authService: AuthService = inject(AuthService);

  constructor() { }

  getCarsByUser(): Observable<CarDto[]> {
    return this.carHttp.getCarsByUser().pipe(
      catchError((error) => {
        console.error('Error al obtener coches del usuario:', error);
        return throwError(() => error);
      })
    );
  }
}
