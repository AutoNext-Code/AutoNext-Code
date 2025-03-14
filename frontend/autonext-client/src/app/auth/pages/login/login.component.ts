import { Component } from '@angular/core';
import { InputComponent } from "../../../shared/components/ui/input/input.component";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'auth-login',
  imports: [InputComponent, CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

}
