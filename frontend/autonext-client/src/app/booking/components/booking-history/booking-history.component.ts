import { ChangeDetectionStrategy, Component } from '@angular/core';
import bookinksData from '../../data/bookings.json';
import { CommonModule } from '@angular/common';
import { SortableThComponent } from '../sortable-th/sortable-th.component';
import { Booking } from '../../booking.model';
import { PaginationComponent } from '../../../shared/components/ui/pagination/pagination.component';

@Component({
  selector: 'booking-history',
  imports: [CommonModule, SortableThComponent, PaginationComponent],
  templateUrl: './booking-history.component.html',
  styleUrl: './booking-history.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BookingHistoryComponent {
  bookings: Booking[] = bookinksData;

  sortColumn: keyof Booking | null = null;
  sortDirection: 'asc' | 'desc' | null = null;

  currentPage = 1;
  totalPages = 3; // o el total real que tengas

  onPageChange(page: number) {
    this.currentPage = page;
    // Aquí puedes cargar los datos de esa página si aplicas paginación real
  }

  onSort(column: string) {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }

    // Ordenación local (datos mock desde JSON)
    this.bookings = [...this.bookings].sort((a, b) => {
      const aValue = a[column];
      const bValue = b[column];

      if (aValue == null) return 1;
      if (bValue == null) return -1;

      if (aValue < bValue) return this.sortDirection === 'asc' ? -1 : 1;
      if (aValue > bValue) return this.sortDirection === 'asc' ? 1 : -1;
      return 0;
    });

    // TODO: Ordenar desde backend más adelante
    // this.loadData(this.sortColumn, this.sortDirection);
  }

  // loadData(column: string | null, direction: 'asc' | 'desc' | null) {
  //   // Aquí harías una petición HTTP al backend pasando los parámetros de ordenación
  //   // this.bookingService.getBookings({ sortBy: column, sortDirection: direction }).subscribe(data => {
  //   //   this.bookings = data;
  //   // });
  // }

  
}
