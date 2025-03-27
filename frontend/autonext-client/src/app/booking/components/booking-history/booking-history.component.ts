import { ChangeDetectionStrategy, Component } from '@angular/core';
import bookinksData from '../../data/bookings.json';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'booking-history',
  imports: [CommonModule],
  templateUrl: './booking-history.component.html',
  styleUrl: './booking-history.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BookingHistoryComponent { 
  bookings = bookinksData;
}