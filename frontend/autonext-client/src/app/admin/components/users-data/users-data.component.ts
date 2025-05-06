import { UserForAdmin } from '@admin/interfaces/user-for-admin.interface';
import { AdminService } from '@admin/services/admin.service';
import { CommonModule } from '@angular/common';
import { Component, effect, inject, Input, Signal } from '@angular/core';
import { AppComponent } from '../../../app.component';
import { HttpErrorResponse } from '@angular/common/http';
import { JobPosition } from '@admin/enums/jobPosition.enum';
import { AuthService } from '@auth/services/auth.service';

@Component({
  selector: 'admin-users-data',
  imports: [CommonModule],
  templateUrl: './users-data.component.html',
  styleUrl: './users-data.component.css'
})
export class UsersDataComponent {

  private adminService = inject(AdminService);
  private appComponent = inject(AppComponent);
  private authService = inject(AuthService);

  @Input() emailSignal!: Signal<string>;

  users: UserForAdmin[] = [];
  jobPositions = Object.entries(JobPosition)
  .filter(([key, value]) => isNaN(Number(key)))
  .map(([key, value]) => ({
    id: value as number,
    name: key === 'Undefined' ? 'Sin asignación' : key.replace(/_/g, ' ')
  }));

  idUser = this.authService.getId();

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

  getJobPositionValue(jobPosition: string | null): number {
    switch (jobPosition) {
      case 'Intern':
        return 1;
      case 'Product_Owner':
        return 2;
      case 'Scrum_Master':
        return 3;
      case 'Developer':
        return 4;
      case 'PEM':
        return 5;
      case 'Branch_Head':
        return 6;
      case null:
      default:
        return 0;
    }
  }

  formatJobPosition(jobPosition: string): string {
    return jobPosition.replace(/_/g, ' ');
  }

  loadUsers(email?: string): void {
    this.adminService.getUsers(email).subscribe({
      next: (users) => {
        this.users = users.map(user => ({
          ...user,
          selectedJobPosition: this.getJobPositionValue(user.jobPosition)
        }));
      },
      error: (error: HttpErrorResponse) => {
        this.appComponent.showToast('error', 'Error al obtener usuarios. ', error.error, 3000);
      },
    });
  }

  toggleRole(user: any) {
    console.log('Toggling role for user:', user.id);
    this.adminService.toggleAdminRole(user.id).subscribe({
      next: () => {
        this.appComponent.showToast('success', 'Rol actualizado', '', 3000, 80);
        this.loadUsers(this.emailSignal());
      },
      error: (error: HttpErrorResponse) => {
        console.error('ERROR AL TOGGLEAR ROL:', error);
        this.appComponent.showToast('error', 'Error al actualizar rol. ', error.error, 3000);
      },
    });
  }
  

}
