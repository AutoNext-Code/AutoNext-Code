import {
  ChangeDetectionStrategy,
  Component,
  computed,
  inject,
  signal,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { SortableThComponent } from '../sortable-th/sortable-th.component';
import { PaginationComponent } from '../../../shared/components/ui/pagination/pagination.component';
import { CardBookingComponent } from '../card-booking/card-booking.component';
import { toSignal } from '@angular/core/rxjs-interop';

import { BookingService } from '@booking/services/booking.service';
import { BookingDTO } from '@booking/interfaces/bookingDTO.interface';
import { AuthService } from '@auth/services/auth.service';

@Component({
  selector: 'booking-history',
  standalone: true,
  imports: [
    CommonModule,
    SortableThComponent,
    PaginationComponent,
    CardBookingComponent,
  ],
  templateUrl: './booking-history.component.html',
  styleUrl: './booking-history.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BookingHistoryComponent {
  private bookingService = inject(BookingService);
  private authService = inject(AuthService);

  // Signals para paginación y filtros
  currentPage = signal(1);
  sortColumn = signal<string>('date');
  sortDirection = signal<'asc' | 'desc'>('asc');
  delegation = signal<string | null>(null);
  carPlate = signal<string | null>(null);
  date = signal<string | null>(null);

  userName = this.authService.getName();

  bookings$ = this.bookingService.bookingList$;
  total$ = this.bookingService.total$;

  total = toSignal(this.bookingService.total$, { initialValue: 0 });
  totalPages = computed(() => Math.ceil(this.total() / 6));

  constructor() {
    this.loadBookings(); // se hace la carga inicial solo una vez
  }

  onSort(column: string) {
    if (this.sortColumn() === column) {
      this.sortDirection.set(this.sortDirection() === 'asc' ? 'desc' : 'asc');
    } else {
      this.sortColumn.set(column);
      this.sortDirection.set('asc');
    }
    this.loadBookings();
  }

  onPageChange(page: number) {
    this.currentPage.set(page);
    this.loadBookings();
  }

  onDelegationChange(value: string) {
    this.delegation.set(value || null);
    this.currentPage.set(1);
    this.loadBookings();
  }

  onCarChange(value: string) {
    this.carPlate.set(value || null);
    this.currentPage.set(1);
    this.loadBookings();
  }

  onDateChange(value: string) {
    this.date.set(value || null);
    this.currentPage.set(1);
    this.loadBookings();
  }

  private loadBookings() {
    this.bookingService.getBookingsByUser({
      page: this.currentPage() - 1,
      sortBy: this.sortColumn(),
      ascending: this.sortDirection() === 'asc',
      delegation: this.delegation() ?? undefined,
      carPlate: this.carPlate() ?? undefined,
      date: this.date() ?? undefined,
    }).subscribe();
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
}
