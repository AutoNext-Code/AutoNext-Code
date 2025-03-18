import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, OnDestroy } from '@angular/core';
import { CustomButtonComponent } from "../custom-button/custom-button.component";
import { AuthService } from '../../auth/services/auth.service';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'shared-header',
  imports: [CommonModule, CustomButtonComponent],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {

  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  public menuOpen: boolean;
  public token: string | null;
  public name: string | null;

  private tokenSubscription: Subscription;

  constructor() {
    this.menuOpen = false;
    this.token = null;
    this.name = null;
    this.tokenSubscription = new Subscription();
  }

  ngOnInit(): void {
    this.tokenSubscription = this.authService.token$.subscribe((newToken) => {
      this.token = newToken;
      if (newToken) {
        this.name = this.authService.getName();
      } else {
        this.name = null;
      }
      // prueba hasta que login sea funcional
      // this.token = "soy un token"
      // this.name = "User"
    });
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  public navigate(): void {
    this.router.navigate(['/']);
  }

  public logout(): void {
    console.log("Logout");
    this.authService.logout();
  }

  ngOnDestroy(): void {
    this.tokenSubscription.unsubscribe();
  }
}
