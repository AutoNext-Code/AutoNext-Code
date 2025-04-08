import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

import { AuthService } from '@auth/services/auth.service';

import { switchMap, take, tap } from 'rxjs/operators';

export const AuthInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  return authService.token$.pipe(
    take(1),
    switchMap((token) => {
      if (token) {
        req = req.clone({
          setHeaders: { Authorization: `Bearer ${token}` },
        });
      }
      return next(req).pipe(
        tap({
          error: (error) => {
            console.error(
              '🔴 [Interceptor] Error en petición:',
              req.url,
              error
            );
          },
        })
      );
    })
  );
};
