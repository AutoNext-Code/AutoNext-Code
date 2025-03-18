import { Component, inject } from '@angular/core';
import { CustomButtonComponent } from '../../../shared/custom-button/custom-button.component';
import { InputComponent } from "../../../shared/components/ui/input/input.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthCardComponent } from "../../components/layouts/auth-card/auth-card.component";
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'auth-login',
  imports: [InputComponent, CustomButtonComponent, CommonModule, FormsModule, AuthCardComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  email: string = "";
  password: string = "";

  loginResponse$: Observable<string | null> | null = null

  constructor( ) {}

  login() {
    console.log('Login');

    console.log(this.email, this.password)

    this.loginResponse$ = this.authService.login(this.email, this.password);


  }
}
