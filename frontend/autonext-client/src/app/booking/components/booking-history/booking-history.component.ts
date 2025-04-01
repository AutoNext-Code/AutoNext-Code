import { ChangeDetectionStrategy, Component, computed, effect, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SortableThComponent } from '../sortable-th/sortable-th.component';
import { PaginationComponent } from '../../../shared/components/ui/pagination/pagination.component';
import { CardBookingComponent } from '../card-booking/card-booking.component';
import { toSignal } from '@angular/core/rxjs-interop';


import { BookingService } from '@booking/services/booking.service';
import { BookingDTO } from '@booking/interfaces/booking.interface';

@Component({
  selector: 'booking-history',
  standalone: true,
  imports: [CommonModule, SortableThComponent, PaginationComponent, CardBookingComponent],
  templateUrl: './booking-history.component.html',
  styleUrl: './booking-history.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BookingHistoryComponent {

  private bookingService = inject(BookingService);
  
  // Signals para paginación y filtros
  currentPage = signal(1);
  sortColumn = signal<string>('date');
  sortDirection = signal<'asc' | 'desc'>('asc');
  delegation = signal<string | null>(null);
  carPlate = signal<string | null>(null);
  date = signal<string | null>(null);

  bookings$ = this.bookingService.bookingList$;
  total$ = this.bookingService.total$;

  total = toSignal(this.bookingService.total$, { initialValue: 0 });
  totalPages = computed(() => Math.ceil(this.total() / 6));


  constructor() {
    effect(() => {
      this.loadBookings();
    });
  }

  onSort(column: string) {
    if (this.sortColumn() === column) {
      this.sortDirection.set(this.sortDirection() === 'asc' ? 'desc' : 'asc');
    } else {
      this.sortColumn.set(column);
      this.sortDirection.set('asc');
    }
  }

  onPageChange(page: number) {
    this.currentPage.set(page);
  }

  onDelegationChange(value: string) {
    this.delegation.set(value || null);
    this.currentPage.set(1);
  }

  onCarChange(value: string) {
    this.carPlate.set(value || null);
    this.currentPage.set(1);
  }

  onDateChange(value: string) {
    this.date.set(value || null);
    this.currentPage.set(1);
  }

  private loadBookings() {
    this.bookingService.getBookingsByUser({
      page: this.currentPage() - 1,
      sortBy: this.sortColumn(),
      ascending: this.sortDirection() === 'asc',
      delegation: this.delegation() ?? undefined,
      carPlate: this.carPlate() ?? undefined,
      date: this.date() ?? undefined
    });
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
        return 'Strike'; // puedes traducirlo si quieres
      case 'Completed':
        return 'Finalizada';
      case 'Blocked':
        return 'Bloqueada';
      default:
        return 'Desconocido';
    }
  }
  
}
