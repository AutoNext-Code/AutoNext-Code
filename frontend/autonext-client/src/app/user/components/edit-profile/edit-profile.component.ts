import {
  Component,
  EventEmitter,
  inject,
  Input,
  OnChanges,
  SimpleChanges,
  Output
} from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { CustomButtonComponent } from '@shared/components/ui/custom-button/custom-button.component';
import { WarningMessageComponent } from '@shared/warning-message/warning-message.component';
import { CustomModalComponent } from '@shared/components/custom-modal/custom-modal.component';
import { InputComponent } from '@shared/components/ui/input/input.component';

import { AuthService } from '@auth/services/auth.service';
import { AuthValidationService } from '@auth/services/auth-validation.service';
import { EditProfileService } from '@user/services/edit-profile.service';

import { AppComponent } from '../../../app.component';
import { throwError } from 'rxjs';

@Component({
  selector: 'edit-profile',
  standalone: true,
  imports: [
    InputComponent,
    FormsModule,
    CustomButtonComponent,
    WarningMessageComponent,
    CustomModalComponent
  ],
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnChanges {

  private authValidationService = inject(AuthValidationService);
  private authService           = inject(AuthService);
  private appComponent          = inject(AppComponent);
  private editProfileService    = inject(EditProfileService);
  private router                = inject(Router);

  @Input({ required: true })
  public originalName!: string;

  @Input({ required: true })
  public originalSurname!: string;

  @Input({ required: true })
  public originalEmail!: string;

  @Output() warningEmitter = new EventEmitter<void>();

  name    = '';
  surname = '';
  email   = '';
  warningView = true;


  ngOnChanges(changes: SimpleChanges): void {
    if (changes['originalName'] && !changes['originalName'].isFirstChange()) {
      this.name = changes['originalName'].currentValue;
    } else if (changes['originalName'] && changes['originalName'].isFirstChange()) {
      this.name = this.originalName;
    }

    if (changes['originalSurname'] && !changes['originalSurname'].isFirstChange()) {
      this.surname = changes['originalSurname'].currentValue;
    } else if (changes['originalSurname'] && changes['originalSurname'].isFirstChange()) {
      this.surname = this.originalSurname;
    }

    if (changes['originalEmail'] && !changes['originalEmail'].isFirstChange()) {
      this.email = changes['originalEmail'].currentValue;
    } else if (changes['originalEmail'] && changes['originalEmail'].isFirstChange()) {
      this.email = this.originalEmail;
    }
  }

  warningClose(): void {
    this.warningEmitter.emit();
    this.warningView = true;
  }

  acceptWarning(): void {
    this.warningView = false;
  }

  saveData(): void {
    this.name    = this.name.trim();
    this.surname = this.surname.trim();
    this.email   = this.email.trim();

    
    if (this.name === "" || this.surname === "" || this.email === "") {
      this.appComponent.showToast('error', 'Problema en el formulario', "Los campos no deben estar vacíos.");
      return ;
    }

    this.editProfileService
      .editProfile(this.name, this.surname, this.email)
      .subscribe({
        next: () => {
          this.appComponent.showToast(
            'success',
            'Perfil modificado correctamente',
            'Su perfil ha sido cambiado sin errores.'
          );
          this.authService.logout();
          this.router.navigateByUrl('/auth/login');
        },
        error: (err: HttpErrorResponse) => {
          console.log(err.error)
          this.appComponent.showToast(
            'error',
            'Error al cambiar el perfil',
            err.error.message
          );
        }
      });
  }
}
