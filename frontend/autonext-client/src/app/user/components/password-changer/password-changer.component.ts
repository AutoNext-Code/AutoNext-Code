import { FormsModule } from '@angular/forms';
import { Component, EventEmitter, Output } from '@angular/core';


import { CustomButtonComponent } from "@shared/components/ui/custom-button/custom-button.component";
import { InputComponent } from "../../../shared/components/ui/input/input.component";

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

  @Output() modalEmitter = new EventEmitter<void>() ;

  passwordOG: string = '';
  passwordNew: string = '';
  passwordConfirm: string = '';

  closeModal() {
    this.modalEmitter.emit();
  }

}
