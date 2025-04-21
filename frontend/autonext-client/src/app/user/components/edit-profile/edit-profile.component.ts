import { Component, EventEmitter, inject, Input, OnInit, Output } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { CustomButtonComponent } from "@shared/components/ui/custom-button/custom-button.component";
import { WarningMessageComponent } from "@shared/warning-message/warning-message.component";
import { CustomModalComponent } from "@shared/components/custom-modal/custom-modal.component";
import { InputComponent } from "@shared/components/ui/input/input.component";

import { AuthService } from '@auth/services/auth.service';
import { AuthValidationService } from '@auth/services/auth-validation.service';

import { EditProfileService } from '@user/services/edit-profile.service';

import { AppComponent } from '../../../app.component';

import { throwError } from 'rxjs';

@Component({
  selector: 'edit-profile',
  imports: [
    InputComponent,
    FormsModule,
    CustomButtonComponent,
    WarningMessageComponent,
    CustomModalComponent
],
  templateUrl: './edit-profile.component.html',
  styleUrl: './edit-profile.component.css'
})
export class EditProfileComponent implements OnInit {

  private authValidationService: AuthValidationService = inject(AuthValidationService);
  private authService: AuthService = inject(AuthService);
  private appComponent: AppComponent = inject(AppComponent);
  private editProfileService: EditProfileService = inject(EditProfileService);  
  private router: Router = inject(Router);
  
  @Input({required: true})
  public originalName!: string;

  @Input({required: true})
  public originalSurname!: string;

  @Input({required: true})
  public originalEmail!: string;

  name = "" ;
  surname = "" ;
  email = "" ;

  warningView = true ;  

  @Output() warningEmitter = new EventEmitter<void>() ;
  @Output() modalEmitter = new EventEmitter<void>() ;

  ngOnInit() {
    
    setTimeout(() => {

      this.name = this.originalName ;
      this.surname = this.originalSurname ;
      this.email = this.originalEmail ;

    }, 500) ;
    
  }

  warningClose() {
    this.warningEmitter.emit();
    this.warningView = true ;
  }

  acceptWarning() {
    this.warningView = false
  }

  closeModal() {
    this.modalEmitter.emit();
    this.warningView = true ;
    this.name = "" ;
    this.surname = "" ;
    this.email = "" ;
  }

  saveData(): void {

    this.name = this.name.trim() ;
    this.surname = this.surname.trim() ;
    this.email = this.email.trim() ;

    if (this.name === "" || this.surname === "" || this.email === "") {
      this.appComponent.showToast('error', 'Problema en el formulario', "Los campos no deben estar vacíos.");
      throwError("La nueva contraseña no puede ser igual a la anterior.") ;
      return ;
    }

    if ((this.name === this.originalName && this.surname === this.originalSurname && this.email === this.originalEmail)) {
      this.appComponent.showToast('error', 'Problema en el formulario', "No se ha hecho ningún cambio.");
      throwError("No se ha hecho ningún cambio.") ;
      return ;
    }
    
    const validacion: string | null = this.authValidationService.validateEmail(this.email) ;
    
    if (validacion !== null) {
      this.appComponent.showToast('error', 'Conflicto de seguridad', validacion);
      return ;
    }

    this.editProfileService.editProfile(this.name, this.surname, this.email).subscribe({
              next: () => {
                this.appComponent.showToast(
                  'success',
                  'Perfil modificado correctamente',
                  'Su perfil a sido cambiado sin errores.'
                );
                this.authService.logout()
                this.router.navigateByUrl('/auth/login');
              },
              error: (err: HttpErrorResponse) => {
                this.appComponent.showToast(
                  'error',
                  'Error al cambiar el perfil',
                  (!err.message.includes("Http failure response") ? err.message : "Existen problemas de conexion")
                );
              },
            }) ;

  }

}
