import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { CustomButtonComponent } from "../custom-button/custom-button.component";
import { AuthService } from '../../auth/services/auth.service';

@Component({
  selector: 'shared-header',
  imports: [CommonModule, CustomButtonComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  private authService: AuthService = inject(AuthService)

  menuOpen = false;

  // constructor(private authService: AuthService) {}

  constructor() {}

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  public logout(): void {
    console.log("Logout")
    this.authService.logout()
  }

}
