import { Component, EventEmitter, inject, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { PasswordService } from '@user/services/password.service';

import { InputComponent } from "@shared/components/ui/input/input.component";
import { CustomButtonComponent } from "@shared/components/ui/custom-button/custom-button.component";
import { AppComponent } from '../../../app.component';
import { throwError } from 'rxjs';
import { AuthValidationService } from '@auth/services/auth-validation.service';

@Component({
  selector: 'password-changer',
  imports: [
    CustomButtonComponent, 
    InputComponent,
    FormsModule
  ],
  templateUrl: './password-changer.component.html',
  styleUrl: './password-changer.component.css'
})
export class PasswordChangerComponent {

  private passwordService: PasswordService = inject(PasswordService);
  private appComponent: AppComponent = inject(AppComponent);
  private authValidation: AuthValidationService = inject(AuthValidationService);

  @Output() modalEmitter = new EventEmitter<void>() ;

  passwordOG: string = '';
  passwordNew: string = '';
  passwordConfirm: string = '';

  warningView = true ;  

  closeModal() {
    this.modalEmitter.emit();
    this.warningView = true ;
  }

  acceptWarning() {
    this.warningView = false
  }

  saveNewPassword(): void {

    if (this.passwordOG === "" || this.passwordConfirm === "" || this.passwordNew === "") {
      this.appComponent.showToast('error', 'Problema en el formulario', "Los campos no deben estar vacíos.");
      throwError("La nueva contraseña no puede ser igual a la anterior.") ;
      return ;
    }

    if (this.passwordOG === this.passwordNew) {
      this.appComponent.showToast('error', 'Conflicto de contraseñas', "La nueva contraseña no puede ser igual a la anterior.");
      throwError("La nueva contraseña no puede ser igual a la anterior.") ;
      return ;
    }

    if (this.passwordNew !== this.passwordConfirm) {
      this.appComponent.showToast('error', 'Conflicto de contraseñas', "La contraseña nueva y su validación no coinciden.");
      throwError("La contraseña nueva y su validación no coinciden.") ;
      return ;
    }

    const validacion: string | null = this.authValidation.validateNewPassword(this.passwordNew) ;

    if (validacion !== null) {
      this.appComponent.showToast('error', 'Conflicto de seguridad', validacion);
    }


    this.passwordService.validatePasswords(this.passwordOG, this.passwordNew);
  }

}
