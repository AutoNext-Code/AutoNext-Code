import { Component, HostListener, inject, ViewChild } from '@angular/core';
import { InputComponent } from '../../../shared/components/ui/input/input.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthCardComponent } from '../../components/layouts/auth-card/auth-card.component';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { CustomButtonComponent } from '../../../shared/components/ui/custom-button/custom-button.component';
import { AppComponent } from '../../../app.component';
import { AuthValidationService } from '../../services/auth-validation.service';

@Component({
  selector: 'auth-login',
  imports: [
    InputComponent,
    CustomButtonComponent,
    CommonModule,
    FormsModule,
    AuthCardComponent,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);
  private authValidation = inject(AuthValidationService);
  private appComponent: AppComponent = inject(AppComponent);

  email: string = '';
  password: string = '';

  loginResponse$: Observable<string | null> | null = null;

  constructor() {}
  login() {
    const validationError = this.authValidation.validateLoginFields(this.email, this.password);
    if (validationError) {
      this.appComponent.showToast('warn', 'Campos inválidos', validationError, 3000);
      return;
    }

    this.authService.login(this.email, this.password).subscribe({
      next: () => {
        const role = this.authService.getRole();
        const redirectUrl = role === 'Admin' ? '/admin-home' : '/home';
        this.router.navigate([redirectUrl]);
      },
      error: () => {
        this.appComponent.showToast(
          'error',
          'Error de autenticación',
          'Correo o contraseña incorrectos.',
          3000
        );
      },
    });
  }

  @HostListener('document:keydown.enter', ['$event'])
  handleEnterKey(event: KeyboardEvent) {
    this.login();
  }
}
