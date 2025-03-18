import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable, map } from 'rxjs';
import { AuthService } from '../auth/services/auth.service';

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
