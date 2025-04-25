import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '@auth/services/auth.service';

import { switchMap, take, tap } from 'rxjs/operators';
import { throwError } from 'rxjs';

export const AuthInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const publicEndpoints = [
    '/api/auth/login',
    '/api/auth/register',
    '/api/auth/forget-password',
    '/api/auth/request-password-reset',
    '/api/auth/confirm-email'
  ];

  const isPublic = publicEndpoints.some(endpoint => req.url.includes(endpoint));

  if (isPublic) {
    return next(req);
  }

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
            console.error('🔴 [Interceptor] Error en petición:', req.url, error);

            if (error.status === 401) {
              const msg = typeof error.error === 'string' ? error.error.toLowerCase() : '';

              if (msg.includes('penalizado') || msg.includes('baneado')) {
                authService.logout();
                router.navigate(['/auth/login']);
              }
            }

            return throwError(() => error);
          },
        })
      );
    })
  );
};
