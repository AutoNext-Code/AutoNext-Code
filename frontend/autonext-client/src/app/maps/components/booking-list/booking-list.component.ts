import { DatePipe } from '@angular/common';
import { Component, Input } from '@angular/core';

import { BookingList } from '@maps/interfaces/bookingList.interface';

@Component({
  selector: 'booking-list',
  imports: [
    DatePipe,
  ],
  templateUrl: './booking-list.component.html',
  styleUrl: './booking-list.component.css'
})
export class BookingListComponent {

  @Input({required: true})
  bookingList!: BookingList ;

}
