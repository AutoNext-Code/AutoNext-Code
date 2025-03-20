import { Component, HostListener, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { InputComponent } from '../../../shared/components/ui/input/input.component';
import { CustomButtonComponent } from '../../../shared/components/ui/custom-button/custom-button.component';

import { AuthService } from '../../services/auth.service';
import { AuthCardComponent } from '../../components/layouts/auth-card/auth-card.component';
import { Observable } from 'rxjs';
import { AuthValidationService } from '../../services/auth-validation.service';
import { AuthHttpService } from '../../services/auth-http.service';

@Component({
  selector: 'auth-register',
  imports: [
        InputComponent,
        CustomButtonComponent,
        CommonModule,
        FormsModule,
        AuthCardComponent,
        RouterLink
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  private authService: AuthService = inject(AuthService);
  private authHttpService: AuthHttpService = inject(AuthHttpService);
  private authValidationService: AuthValidationService = inject(AuthValidationService);
  private router: Router = inject(Router);

  email: string = '';
  password: string = '';
  password2: string = '';
  name: string = '';
  surname: string = '';
  carPlate: string = '';

  register1: boolean = true;
  register2: boolean = false;

  loginResponse$: Observable<string | null> | null = null;

  constructor() {}

  register() {

    if(this.authValidationService.validateRegisterFields(this.name, this.surname, this.email, this.password, this.carPlate)==null) {
      this.authHttpService.register(this.name, this.surname, this.email, this.password, this.carPlate)

      this.router.navigateByUrl("/auth/login")
    }

  }

  forward() {

    if((this.password === this.password2) && (this.authValidationService.validateSwitchFields(this.email, this.password))){
      this.register2 = true ;
      this.register1 = false ;
    }

  }
  back() {
    this.register2 = false ;
    this.register1 = true ; 
  }

  @HostListener('document:keydown.enter', ['$event'])
  handleEnterKey(event: KeyboardEvent) {
    this.register();
  }
}
