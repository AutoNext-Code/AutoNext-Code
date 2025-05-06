import { UserForAdmin } from '@admin/interfaces/user-for-admin.interface';
import { AdminService } from '@admin/services/admin.service';
import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'admin-users-data',
  imports: [CommonModule, FormsModule],
  templateUrl: './users-data.component.html',
  styleUrl: './users-data.component.css'
})
export class UsersDataComponent {

  private adminService = inject(AdminService);

  users: UserForAdmin[] = [];

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(email?: string): void {
    this.adminService.getUsers(email).subscribe({
      next: (users) => {
        this.users = users; // Almacenamos los usuarios recibidos
      },
      error: (error) => {
        console.error('Error al cargar usuarios:', error);
      },
    });
  }


}
