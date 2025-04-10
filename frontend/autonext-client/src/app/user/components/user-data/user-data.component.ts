import { Component, inject, NgZone, AfterViewInit, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../../../app.component';
import { UserDto } from '@user/interfaces/user.interface';
import { CommonModule } from '@angular/common';
import { ProfileService } from '../../services/profile.service';
import { PasswordChangerComponent } from "../password-changer/password-changer.component";

@Component({
  selector: 'profile-user-data',
  imports: [CommonModule, PasswordChangerComponent],
  templateUrl: './user-data.component.html',
  styleUrls: ['./user-data.component.css']
})
export class UserDataComponent implements OnInit, AfterViewInit, OnDestroy {

  private router: Router = inject(Router);
  private ngZone: NgZone = inject(NgZone);
  private appComponent: AppComponent = inject(AppComponent);
  private profileService: ProfileService = inject(ProfileService);
  

  user: UserDto | null = null;

  passwdModal:boolean = true ;
  isMobile = false;

  ngOnInit(): void {
    this.loadUserProfile();
  }

  public navigate(path: string): void {
    this.router.navigate([path]);
  }

  public warning(): void {
    this.appComponent.showToast('warn', 'No implementado', "", 3000, 80);
  }

  public loadUserProfile(): void {
    this.profileService.getDataProfile().subscribe({
      next: (data: UserDto) => {
        this.user = data;
      },
      error: (error) => {
        console.error('Error loading profile', error);
      },
    });
  }
  
  public passwordModal(): void {

    this.passwdModal = false ;

  }

  cancelChanges() {

    this.appComponent.showToast('warn', 'Cambios Desechados', "No se guardaron los cambios realizados");
    this.closeModal() ;

  }

  closeModal() {
    this.passwdModal = true ;
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
