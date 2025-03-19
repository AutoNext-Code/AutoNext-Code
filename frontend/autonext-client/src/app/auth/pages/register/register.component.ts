import { Component, HostListener, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { InputComponent } from '../../../shared/components/ui/input/input.component';
import { CustomButtonComponent } from '../../../shared/components/ui/custom-button/custom-button.component';

import { AuthService } from '../../services/auth.service';
import { AuthCardComponent } from '../../components/layouts/auth-card/auth-card.component';
import { Observable } from 'rxjs';

@Component({
  selector: 'auth-register',
  imports: [
        InputComponent,
        CustomButtonComponent,
        CommonModule,
        FormsModule,
        AuthCardComponent,
        RouterLink
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  email: string = '';
  password: string = '';
  password2: string = '';
  name: string = '';
  surname: string = '';
  carPlate: string = '';

  loginResponse$: Observable<string | null> | null = null;

  constructor() {}

  login() {
    this.authService.login(this.email, this.password).subscribe({
      next: (token) => {
        const role = this.authService.getRole();

        if (role === 'Admin') {
          this.router.navigate(['/admin-home']);
        } else {
          this.router.navigate(['/home']);
        }
      },
      error: (err) => {
        console.error('Error en el login:', err);
      },
    });
  }

  @HostListener('document:keydown.enter', ['$event'])
  handleEnterKey(event: KeyboardEvent) {
    this.login();
  }
}
