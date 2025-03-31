import { Component } from '@angular/core';
import { HeaderComponent } from '@shared/header/header.component';
import { MapsComponent } from '@maps/pages/maps.component';
import { BookingFormComponent } from '@user/components/booking-form/booking-form.component';

@Component({
  imports: [HeaderComponent, MapsComponent, BookingFormComponent],
  templateUrl: './home-user-page.component.html',
  styleUrl: './home-user-page.component.css'
})
export class HomeUserPageComponent {

  selectedMap: string = '';

}