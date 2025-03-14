import { Component } from '@angular/core';
import { CustomButtonComponent } from '../../../shared/components/ui/custom-button/custom-button.component';
import { InputComponent } from "../../../shared/components/ui/input/input.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'auth-login',
  imports: [InputComponent, CustomButtonComponent, CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  login() {
    console.log('Login');
  }
}
