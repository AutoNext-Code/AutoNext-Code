import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { CustomButtonComponent } from "../custom-button/custom-button.component";

@Component({
  selector: 'shared-header',
  imports: [CommonModule, CustomButtonComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  menuOpen = false;

  constructor() {}

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

}