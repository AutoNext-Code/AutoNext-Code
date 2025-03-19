import { Component, HostListener, inject } from '@angular/core';
import { InputComponent } from '../../../shared/components/ui/input/input.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthCardComponent } from '../../components/layouts/auth-card/auth-card.component';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CustomButtonComponent } from '../../../shared/components/ui/custom-button/custom-button.component';

@Component({
  selector: 'auth-login',
  imports: [
    InputComponent,
    CustomButtonComponent,
    CommonModule,
    FormsModule,
    AuthCardComponent,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  email: string = '';
  password: string = '';

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
