import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

import { AuthCardComponent } from '@auth/components/auth-card/auth-card.component';
import { CustomButtonComponent } from '@shared/components/ui/custom-button/custom-button.component';
import { InputComponent } from '@shared/components/ui/input/input.component';

import { AppComponent } from '../../../app.component';

import { AuthValidationService } from '@auth/services/auth-validation.service';
import { AuthManager } from '@auth/services/authmanager.service';

@Component({
  selector: 'app-reset-password',
  imports: [
    AuthCardComponent,
    InputComponent,
    CommonModule,
    CustomButtonComponent,
    FormsModule,
  ],
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.css',
})
export class ResetPasswordComponent {
  private authValidation = inject(AuthValidationService);
  private authManager = inject(AuthManager);
  private appComponent: AppComponent = inject(AppComponent);
  private router: Router = inject(Router);

  new_password: string = '';
  repeat_password: string = '';

  sendResponse: Observable<string | null> | null = null;

  resetPassword() {
    const validatePassword = this.authValidation.validatePassword(this.new_password);

    if (validatePassword) {
      this.appComponent.showToast('warn', 'Error en el formato', validatePassword);
      return;
    }

    if (validatePassword == null) {
      this.sendResponse = this.authManager.requestPasswordReset(this.new_password);
    }

    if (this.new_password !== this.repeat_password) {
      this.appComponent.showToast(
        'warn',
        'Las contraseñas no coinciden',
        'Por favor, verifica las contraseñas ingresadas.'
      );
      return;
    }

    this.sendResponse?.subscribe({
      next: () => {
        this.appComponent.showToast(
          'success',
          'Su contraseña ha sido actualizada.',
          'Ya puede iniciar sesión.'
        );
        this.router.navigate(['/auth/login']);
      },
      error: (err: HttpErrorResponse) => {
        this.appComponent.showToast(
          'error',
          'Error al hacer el cambio de la contraseña.',
          err.message
        );
      },
    });
  }
}
