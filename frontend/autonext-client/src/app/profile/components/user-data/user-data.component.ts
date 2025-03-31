import { Component, inject, NgZone, AfterViewInit, OnDestroy } from '@angular/core';
import { CustomButtonComponent } from "../../../shared/components/ui/custom-button/custom-button.component";
import { Router } from '@angular/router';
import { AppComponent } from '../../../app.component';

@Component({
  selector: 'profile-user-data',
  imports: [],
  templateUrl: './user-data.component.html',
  styleUrls: ['./user-data.component.css']
})
export class UserDataComponent implements AfterViewInit, OnDestroy {

  private router: Router = inject(Router);
  private ngZone: NgZone = inject(NgZone);
  private appComponent: AppComponent = inject(AppComponent);

  user = {
    name: "User user",
    email: "user@example.com",
    jobPosition: "developer",
    workCenterName: "Málaga",
    strikes: 1,
  }

  isMobile = false;

  public navigate(path: string): void {
    console.log(path);
    this.router.navigate([path]);
  }

  public warning(): void {
    this.appComponent.showToast('warn', 'No implementado', "", 3000, 80);
  }

  ngAfterViewInit() {
    this.checkScreenSize();
    window.addEventListener('resize', this.checkScreenSize.bind(this));
  }

  checkScreenSize = () => {
    this.ngZone.run(() => {
      this.isMobile = window.innerWidth < 768;
    });
  };

  ngOnDestroy() {
    window.removeEventListener('resize', this.checkScreenSize);
  }
}
