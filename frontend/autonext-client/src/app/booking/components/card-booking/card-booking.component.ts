import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'card-booking',
  imports: [CommonModule],
  templateUrl: './card-booking.component.html',
  styleUrl: './card-booking.component.css'
})
export class CardBookingComponent {

  @Input() booking: any;

}
