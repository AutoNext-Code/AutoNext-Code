import { AdminService } from '@admin/services/admin.service';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputComponent } from '@shared/components/ui/input/input.component';
import { AppComponent } from '../../../app.component';

@Component({
  imports: [InputComponent, FormsModule],
  templateUrl: './booking-management-page.component.html',
  styleUrl: './booking-management-page.component.css',
})
export class BookingManagementPageComponent implements OnInit {

  private appComponent: AppComponent = inject(AppComponent);

  adminService: AdminService = inject(AdminService);

  parkingLimit: number | null = null;
  newLimit: number | null = null;

  ngOnInit(): void {
    this.fetchParkingLimit();
  }

  fetchParkingLimit(): void {
    this.adminService.getParkingLimit().subscribe({
      next: (limit) => {
        this.parkingLimit = limit;
        this.newLimit = limit;
      },
      error: () => {
        this.appComponent.showToast('error', 'Error al cargar el limite. ',"", 3000);
      },
    });
  }

  saveNewLimit(): void {
    if (this.newLimit !== null) {
      this.adminService.updateParkingLimit(this.newLimit).subscribe({
        next: (message) => {
          this.appComponent.showToast('success', message, "", 3000);
          this.fetchParkingLimit();
        },
        error: (error) => {
          this.fetchParkingLimit();
          const errorMsg = error?.error || 'No se pudo actualizar el límite de estacionamiento.';
          this.appComponent.showToast('error', errorMsg, "", 3000);
        },
      });
    }
  }

}
