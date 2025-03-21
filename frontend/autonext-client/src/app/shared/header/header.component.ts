import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, OnDestroy } from '@angular/core';

import { AuthService } from '@auth/services/auth.service';
import { CustomButtonComponent } from '../components/ui/custom-button/custom-button.component';

import { Subscription } from 'rxjs';

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
    });
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  public navigate(path: string): void {
    this.router.navigate([path]);
  }

  public logout(): void {
    console.log("Logout");
    this.authService.logout();
    this.navigate('/auth/login');
  }

  ngOnDestroy(): void {
    this.tokenSubscription.unsubscribe();
  }
}
