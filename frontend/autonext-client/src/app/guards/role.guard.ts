import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { AuthService } from '@auth/services/auth.service';

import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): Observable<boolean> {
    return this.authService.token$.pipe(
      map((token) => {
        if (token && this.authService.getRole()?.toLowerCase() === 'admin') {
          return true;
        }
        this.router.navigate(['/home']);
        return false;
      })
    );
  }
}
