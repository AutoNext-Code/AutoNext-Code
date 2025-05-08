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
  isEditing: { [key: number]: boolean } = {};

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
    if (jobPosition === 'Undefined') {
      return 'Sin asignación';
    }
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

  toggleEdit(userId: number) {
    this.isEditing[userId] = !this.isEditing[userId];
  }

  saveChanges(user: UserForAdmin) {
    const selectedJobPositionIndex = (document.querySelector(`select[name="jobPosition"][value="${user.id}"]`) as HTMLSelectElement)?.value;
    const selectedWorkCenter = (document.querySelector(`select[name="workCenter"][value="${user.id}"]`) as HTMLSelectElement)?.value;
  
    const selectedJobPosition = JobPosition[+selectedJobPositionIndex];
  
    this.adminService.updateJobPosition(user.id, selectedJobPosition).subscribe({
      next: () => {
        this.adminService.updateWorkCenter(user.id, +selectedWorkCenter).subscribe({
          next: () => {
            this.appComponent.showToast('success', 'Centro  y puesto de trabajo actualizados', '', 3000, 80);
            this.isEditing[user.id] = false;
            this.loadUsers(this.emailSignal());
          },
          error: (error: HttpErrorResponse) => {
            this.appComponent.showToast('error', 'Error al guardar cambios en centro de trabajo.', error.error, 3000);
          },
        });
      },
      error: (error: HttpErrorResponse) => {
        this.appComponent.showToast('error', 'Error al guardar cambios en puesto de trabajo.', error.error, 3000);
      },
    });
  }
  

}
