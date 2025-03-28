import { Component } from '@angular/core';
import { HeaderComponent } from '@shared/header/header.component';
import { BookingHistoryComponent } from '../../components/booking-history/booking-history.component';
import { SelectDepComponent } from "../../../shared/components/ui/select-dep/select-dep.component";

@Component({
  selector: 'app-booking-page',
  imports: [HeaderComponent, BookingHistoryComponent],
  templateUrl: './booking-page.component.html'
})
export class BookingPageComponent {

}
