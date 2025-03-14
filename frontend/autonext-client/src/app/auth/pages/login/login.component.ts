import { Component } from '@angular/core';
import { CustomButtonComponent } from '../../../shared/custom-button/custom-button.component';



@Component({
  selector: 'auth-login',
  imports: [CustomButtonComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  login() {
    console.log('Login');
  }
}
