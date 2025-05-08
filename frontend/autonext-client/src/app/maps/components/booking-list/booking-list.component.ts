import { CommonModule, DatePipe } from '@angular/common';
import { Component, EventEmitter, inject, Input, Output } from '@angular/core';

import { BookingList } from '@maps/interfaces/bookingList.interface';
import { PaginationComponent } from '../../../shared/components/ui/pagination/pagination.component';
import { CustomModalComponent } from '@shared/components/custom-modal/custom-modal.component';
import { BookingService } from '@booking/services/booking.service';
import { AppComponent } from '../../../app.component';

@Component({
  selector: 'booking-list',
  imports: [DatePipe, CommonModule, CustomModalComponent, PaginationComponent],
  templateUrl: './booking-list.component.html',
  styleUrl: './booking-list.component.css',
})
export class BookingListComponent {
  private bookingService = inject(BookingService);
  private appComponent = inject(AppComponent);


  @Input({ required: true })
  bookingList!: BookingList;
  currentPage: number = 1;

  modalVisible = false;
  selectedBookingId: number | null = null;


  @Output()
  pageChanger: EventEmitter<number> = new EventEmitter();

  pageChange(change: number): void {
    this.currentPage = change;
    this.pageChanger.emit(change);
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

  // TODO CAMBIAR AL METODO DEL ADMIN CUANDO HAYA QUE IMPLEMENTARLO
  cancelBooking(id: number) {
    this.bookingService.cancelBooking(id).subscribe({
      next: () => {
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
