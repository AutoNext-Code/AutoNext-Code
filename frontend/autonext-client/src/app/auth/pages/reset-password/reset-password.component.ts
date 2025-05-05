import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';

import { AuthCardComponent } from '@auth/components/auth-card/auth-card.component';
import { CustomButtonComponent } from '@shared/components/ui/custom-button/custom-button.component';
import { InputComponent } from '@shared/components/ui/input/input.component';

import { AppComponent } from '../../../app.component';
import { AuthValidationService } from '@auth/services/auth-validation.service';
import { AuthManager } from '@auth/services/authmanager.service';

@Component({
  selector: 'app-reset-password',
  standalone: true,
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
  private route: ActivatedRoute = inject(ActivatedRoute);

  new_password: string = '';
  repeat_password: string = '';

  resetPassword(): void {
    const token = this.route.snapshot.paramMap.get('token');

    if (!token) {
      this.appComponent.showToast(
        'error',
        'Enlace no válido',
        'El enlace para restablecer la contraseña no es válido o ha expirado.'
      );
      return;
    }

    const validationError = this.authValidation.validatePassword(
      this.new_password
    );

    if (validationError != null) {
      this.appComponent.showToast(
        'warn',
        'Error en el formato',
        validationError
      );
      return;
    }

    if (this.new_password !== this.repeat_password) {
      this.appComponent.showToast(
        'warn',
        'Las contraseñas no coinciden',
        'Por favor, verifica las contraseñas ingresadas.'
      );
      return;
    }

    console.log('🧪 Enviando reset con contraseña:', this.new_password);
    this.authManager.requestNewPassword(token, this.new_password).subscribe({
      next: () => {
        this.appComponent.showToast(
          'success',
          'Contraseña actualizada',
          'Su contraseña ha sido restablecida correctamente. Ya puede iniciar sesión.'
        );
        this.router.navigate(['/auth/login']);
      },
      error: (err: HttpErrorResponse) => {
        this.appComponent.showToast(
          'error',
          'Error al restablecer la contraseña',
          err.message
        );
      },
    });
  }
}
