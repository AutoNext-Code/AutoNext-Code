import {
  ChangeDetectionStrategy,
  Component,
  computed,
  inject,
  signal,
} from '@angular/core';
import { CommonModule } from '@angular/common';

import { toSignal } from '@angular/core/rxjs-interop';

import { AppComponent } from '../../../app.component';
import { PaginationComponent } from '@shared/components/ui/pagination/pagination.component';

import { BookingService } from '@booking/services/booking.service';

import { AuthService } from '@auth/services/auth.service';
import { CancelModalComponent } from '../cancel-modal/cancel-modal.component';

@Component({
  selector: 'booking-history',
  standalone: true,
  imports: [CommonModule, PaginationComponent, CancelModalComponent],
  templateUrl: './booking-history.component.html',
  styleUrl: './booking-history.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BookingHistoryComponent {
  private bookingService = inject(BookingService);
  private authService = inject(AuthService);
  private appComponent: AppComponent = inject(AppComponent);

  currentPage = signal(1);
  sortDirection = signal<'asc' | 'desc'>('desc');
  workCenterId = signal<number | null>(null);
  carId = signal<number | null>(null);
  date = signal<string | null>(null);

  userName = this.authService.getName();

  bookings$ = this.bookingService.bookingList$;
  total$ = this.bookingService.total$;

  cars = signal<{ id: number; name: string }[]>([]);
  workCenters = signal<{ id: number; name: string }[]>([]);

  total = toSignal(this.bookingService.total$, { initialValue: 0 });
  totalPages = computed(() => Math.ceil(this.total() / 6));

  modalVisible = false;
  selectedBookingId: number | null = null;

  constructor() {
    this.loadBookings();
    this.loadUserCars();
    this.loadWorkCenters();
  }

  onPageChange(page: number) {
    this.currentPage.set(page);
    this.loadBookings();
  }

  onDelegationChange(value: string) {
    this.workCenterId.set(value ? +value : null);
    this.currentPage.set(1);
    this.loadBookings();
  }

  onCarChange(value: string) {
    this.carId.set(value ? +value : null);
    this.currentPage.set(1);
    this.loadBookings();
  }

  onDateChange(value: string) {
    this.date.set(value || null);
    this.currentPage.set(1);
    this.loadBookings();
  }

  toggleSortDirection() {
    this.sortDirection.set(this.sortDirection() === 'asc' ? 'desc' : 'asc');
    this.currentPage.set(1);
    this.loadBookings();
  }

  confirmBooking(id: number) {
    this.bookingService.updateConfirmationStatus(id, 'Confirmed').subscribe({
      next: () => this.loadBookings(),
      error: (err) => console.error('Error al confirmar reserva:', err),
    });
  }

  cancelBooking(id: number) {
    console.log('[CANCEL ID]', id);
    this.bookingService.cancelBooking(id).subscribe({
      next: () => {
        this.loadBookings();
        this.appComponent.showToast(
          'success',
          'Reserva cancelada',
          '',
          3000,
          80
        );
      },
      error: (err) => {
        console.error('Error al cancelar reserva:', err),
          this.appComponent.showToast(
            'error',
            'Error al cancelar reserva',
            err.message,
            3000,
            80
          );
      },
    });
  }

  getSelectValue(event: Event): string {
    return (event.target as HTMLSelectElement).value;
  }

  getStatusLabel(status: string | null): string {
    switch (status) {
      case 'Active':
        return 'Activa';
      case 'Pending':
        return 'Reservada';
      case 'Cancelled':
        return 'Cancelada';
      case 'Strike':
        return 'Strike';
      case 'Completed':
        return 'Finalizada';
      case 'Blocked':
        return 'Bloqueada';
      default:
        return 'Desconocido';
    }
  }

  private loadBookings() {
    this.bookingService
      .getBookingsByUser({
        page: this.currentPage() - 1,
        ascending: this.sortDirection() === 'asc',
        date: this.date() ?? undefined,
        workCenterId: this.workCenterId() ?? undefined,
        carId: this.carId() ?? undefined,
      })
      .subscribe();
  }

  private loadUserCars() {
    this.bookingService.getUserCars().subscribe({
      next: (cars) => this.cars.set(cars),
      error: (err) => console.error('Error al cargar coches:', err),
    });
  }

  private loadWorkCenters() {
    this.bookingService.getWorkCenters().subscribe({
      next: (centers) => this.workCenters.set(centers),
      error: (err) => console.error('Error al cargar delegaciones:', err),
    });
  }



  openCancelModal(bookingId: number): void {
    this.selectedBookingId = bookingId;
    this.modalVisible = true;
  }

  handleModalCancel(): void {
    this.modalVisible = false;
    this.selectedBookingId = null;
  }

  handleModalConfirm(): void {
    if (this.selectedBookingId !== null) {
      this.cancelBooking(this.selectedBookingId);
      this.modalVisible = false;
      this.selectedBookingId = null;
    }
  }
}
