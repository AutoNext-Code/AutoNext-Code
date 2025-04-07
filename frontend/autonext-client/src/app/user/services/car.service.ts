import { inject, Injectable } from '@angular/core';
import { CarHttpService } from './car-http.service';
import { AuthService } from '@auth/services/auth.service';
import { CarDto } from '@user/interfaces/car.interface';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class CarService {
  private carHttp: CarHttpService = inject(CarHttpService);
  private authService: AuthService = inject(AuthService);

  constructor() {}

  getCarsByUser(): Observable<CarDto[]> {
    const token = this.authService.getToken();

    if (!token) {
      console.error('No hay token disponible');
      throw new Error('No authentication token found');
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.carHttp.getCarsByUser(headers).pipe(
      catchError((error) => {
        console.error('Error al obtener coches del usuario:', error);
        return throwError(() => error);
      })
    );
  }
}
