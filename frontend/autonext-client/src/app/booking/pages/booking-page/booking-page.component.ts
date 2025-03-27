import { Component } from '@angular/core';
import { HeaderComponent } from '@shared/header/header.component';
import { BookingHistoryComponent } from '../../components/booking-history/booking-history.component';

@Component({
  selector: 'app-booking-page',
  imports: [HeaderComponent, BookingHistoryComponent],
  templateUrl: './booking-page.component.html'
})
export class BookingPageComponent {

}
