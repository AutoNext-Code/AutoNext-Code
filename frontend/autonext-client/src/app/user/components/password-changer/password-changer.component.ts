import { Component, EventEmitter, inject, Output } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { PasswordService } from '@user/services/password.service';

import { InputComponent } from "@shared/components/ui/input/input.component";
import { WarningMessageComponent } from "@shared/warning-message/warning-message.component";
import { CustomModalComponent } from "@shared/components/custom-modal/custom-modal.component";
import { CustomButtonComponent } from "@shared/components/ui/custom-button/custom-button.component";

import { AuthValidationService } from '@auth/services/auth-validation.service';

import { AppComponent } from '../../../app.component';

import { throwError } from 'rxjs';

@Component({
  selector: 'password-changer',
  imports: [
    CustomButtonComponent,
    InputComponent,
    FormsModule,
    WarningMessageComponent,
    CustomModalComponent
],
  templateUrl: './password-changer.component.html',
  styleUrl: './password-changer.component.css'
})
export class PasswordChangerComponent {

  private passwordService: PasswordService = inject(PasswordService);
  private appComponent: AppComponent = inject(AppComponent);
  private authValidation: AuthValidationService = inject(AuthValidationService);

  @Output() warningEmitter = new EventEmitter<void>() ;
  @Output() modalEmitter = new EventEmitter<void>() ;

  passwordOG: string = '';
  passwordNew: string = '';
  passwordConfirm: string = '';

  warningView = true ;  

  warningClose() {
    this.warningEmitter.emit();
    this.warningView = true ;
  }

  closeModal() {
    this.modalEmitter.emit();
    this.warningView = true ;
    this.passwordOG = "" ;
    this.passwordNew = "" ;
    this.passwordConfirm = "" ;
  }

  acceptWarning() {
    this.warningView = false
  }

  saveNewPassword(): void {

    this.passwordOG = this.passwordOG.trim() ;
    this.passwordNew = this.passwordNew.trim() ;
    this.passwordConfirm = this.passwordConfirm.trim() ;

    if (this.passwordOG === this.passwordNew) {
      this.appComponent.showToast('error', 'Conflicto de contraseñas', "La nueva contraseña no puede ser igual a la anterior.");
      return ;
    }

    if (this.passwordNew !== this.passwordConfirm) {
      this.appComponent.showToast('error', 'Conflicto de contraseñas', "La contraseña nueva y su validación no coinciden.");
      return ;
    }

    const validacion: string | null = this.authValidation.validateNewPassword(this.passwordNew) ;

    if (validacion !== null) {
      this.appComponent.showToast('error', 'Conflicto de seguridad', validacion);
      return ;
    }


    this.passwordService.validatePasswords(this.passwordOG, this.passwordNew).subscribe({
          next: () => {
            this.appComponent.showToast(
              'success',
              'Contraseñas modificadas correctamente',
              'Su contraseña a sido sustituida sin errores.'
            );
            this.closeModal()
          },
          error: (err: HttpErrorResponse) => {
            this.appComponent.showToast(
              'error',
              'Error al cambiar la contraseña',
              err.message
            );
          },
        });
  }

}
