import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';

import { InputComponent } from '@shared/components/ui/input/input.component';
import { CustomButtonComponent } from '@shared/components/ui/custom-button/custom-button.component';

import { AuthManager } from '@auth/services/authmanager.service';
import { AuthValidationService } from '@auth/services/auth-validation.service';

import { AuthCardComponent } from '@auth/components/auth-card/auth-card.component';

import { Observable } from 'rxjs';
import { AppComponent } from '../../../app.component';


@Component({
  selector: 'auth-register',
  imports: [
    InputComponent,
    CustomButtonComponent,
    CommonModule,
    FormsModule,
    AuthCardComponent,
    RouterLink,
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  private authManager = inject(AuthManager);
  private authValidation: AuthValidationService = inject(AuthValidationService);

  private appComponent: AppComponent = inject(AppComponent);
  private router: Router = inject(Router);

  email: string = '';
  password: string = '';
  password2: string = '';
  name: string = '';
  surname: string = '';
  carPlate: string = '';

  register1: boolean = true;
  register2: boolean = false;

  registerResponse: Observable<string | null> | null = null;

  constructor() {}

  register() {
    const validationError = this.authValidation.validateRegisterFields(
      this.name,
      this.surname,
      this.email,
      this.password,
      this.carPlate
    );

    if (validationError) {
      this.appComponent.showToast('warn', 'Campos inválidos', validationError);
      return;
    }

    if (validationError == null) {
      this.registerResponse = this.authManager.register(
        this.name,
        this.surname,
        this.email,
        this.password,
        this.carPlate
      );

      this.registerResponse.subscribe({
        next: () => {
          this.appComponent.showToast(
            'success',
            'Registro exitoso',
            'Te has registrado correctamente, verifica tu correo para confirmar tu cuenta.'
          );
          this.router.navigate(['/auth/login']);
        },
        error: (err: HttpErrorResponse) => {
          this.appComponent.showToast(
            'error',
            'Error en el registro',
            err.message
          );
        },
      });
    }
  }

  forward() {
    if (this.password !== this.password2) {
      this.appComponent.showToast(
        'warn',
        'Las contraseñas no coinciden',
        'Por favor, verifica las contraseñas ingresadas.'
      );
      return;
    }

    const validationError = this.authValidation.validateSwitchFields(
      this.email,
      this.password
    );
    if (validationError) {
      this.appComponent.showToast('warn', 'Campos inválidos', validationError);
      return;
    }

    this.register2 = true;
    this.register1 = false;
  }

  back() {
    this.register2 = false;
    this.register1 = true;
  }
}
