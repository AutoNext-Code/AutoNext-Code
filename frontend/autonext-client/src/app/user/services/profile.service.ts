import { inject, Injectable } from '@angular/core';
import { ProfileHttpService } from './profile-http.service';
import { HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { UserDto } from '@user/interfaces/user.interface';
import { AuthService } from '@auth/services/auth.service';

@Injectable({ providedIn: 'root' })
export class ProfileService {
  private profileHttp: ProfileHttpService = inject(ProfileHttpService);
  private authService: AuthService = inject(AuthService);

  constructor() {}

  getDataProfile(): Observable<UserDto> {
    const token = this.authService.getToken();

    if (!token) {
      console.error('No hay token disponible');
      throw new Error('No authentication token found');
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.profileHttp.getDataProfile(headers).pipe();
  }
}
