import { UserForAdmin } from '@admin/interfaces/user-for-admin.interface';
import { AdminService } from '@admin/services/admin.service';
import { CommonModule } from '@angular/common';
import { Component, effect, inject, Input, Signal } from '@angular/core';
import { AppComponent } from '../../../app.component';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'admin-users-data',
  imports: [CommonModule],
  templateUrl: './users-data.component.html',
  styleUrl: './users-data.component.css'
})
export class UsersDataComponent {

  private adminService = inject(AdminService);
  private appComponent = inject(AppComponent);

  @Input() emailSignal!: Signal<string>;

  users: UserForAdmin[] = [];

  constructor() {
    effect(() => {
      const email = this.emailSignal();
      this.loadUsers(email);
    });
  }

  getWorkCenterValue(workCenter: string | null): number {
    switch (workCenter) {
      case 'Madrid':
        return 1;
      case 'Málaga':
        return 2;
      case null:
      default:
        return 0;
    }
  }

  loadUsers(email?: string): void {
    this.adminService.getUsers(email).subscribe({
      next: (users) => {
        this.users = users;
      },
      error: (error: HttpErrorResponse) => {
        console.error('ERROR AL AAAA al cargar usuarios:', error);
        this.appComponent.showToast('error', 'Error al obtener usuarios. ' , error.error, 3000);
      },
    });
  }

}
