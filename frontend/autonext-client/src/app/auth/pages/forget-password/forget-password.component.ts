import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { AuthManager } from '@auth/services/authmanager.service';
import { AuthCardComponent } from '@auth/components/auth-card/auth-card.component';
import { AuthValidationService } from '@auth/services/auth-validation.service';

import { CustomButtonComponent } from '@shared/components/ui/custom-button/custom-button.component';

import { InputComponent } from '@shared/components/ui/input/input.component';

import { AppComponent } from '../../../app.component';

import { Observable } from 'rxjs';


@Component({
  selector: 'app-forget-password',
  imports: [
    AuthCardComponent,
    InputComponent,
    CommonModule,
    CustomButtonComponent,
    FormsModule,
    RouterLink
  ],
  templateUrl: './forget-password.component.html',
  styleUrl: './forget-password.component.css',
})
export class ForgetPasswordComponent {
  private authValidation = inject(AuthValidationService);
  private authManager = inject(AuthManager);

  private appComponent: AppComponent = inject(AppComponent);
  private router: Router = inject(Router);

  email: string = '';

  sendResponse: Observable<string | null> | null = null;

  sendEmail() {
    console.log(this.email);
    const validateEmail = this.authValidation.validateEmail(this.email);

    if (validateEmail) {
      this.appComponent.showToast('warn', 'Error en el formato', validateEmail);
      return;
    }

    if (validateEmail != null) {
      this.appComponent.showToast('warn', 'Error en el formato', validateEmail);
      return;
    }

    this.sendResponse = this.authManager.requestPasswordReset(this.email);

    const sub = this.sendResponse.subscribe({
      next: () => {
        this.appComponent.showToast(
          'success',
          'Compruebe su correo',
          'Correo enviado. Revisa tu bandeja de entrada para reestablecer tu contraseña.'
        );
        this.router.navigate(['/auth/login']);
      },
      error: (err: HttpErrorResponse) => {
        if (err.status === 404 || err.status === 401) {
          this.appComponent.showToast(
            'success',
            'Compruebe su correo',
            'Correo enviado. Revisa tu bandeja de entrada.'
          );
          this.router.navigate(['/auth/login']);
          sub.unsubscribe();
        } else {
          this.appComponent.showToast('error', 'Error inesperado', err.message);
        }
      },
    });
  }
}
