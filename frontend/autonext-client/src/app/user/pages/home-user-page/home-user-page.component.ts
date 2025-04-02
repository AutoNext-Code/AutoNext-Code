import { Component } from '@angular/core';

import { MapsComponent } from '@maps/pages/maps.component';

import { BookingFormComponent } from '@maps/components/booking-form/booking-form.component';

import { HeaderComponent } from '@shared/header/header.component';
import { LoaderComponent } from "@shared/loader/loader.component";

@Component({
  imports: [HeaderComponent, MapsComponent, BookingFormComponent, LoaderComponent],
  templateUrl: './home-user-page.component.html',
  styleUrl: './home-user-page.component.css'
})
export class HomeUserPageComponent {

  selectedMap: number = 0;  // Para guardar el mapa seleccionado
  isMapLoaded = false;       // Para manejar la carga del mapa

  updateSelectedMap(idmap: number) {
    this.isMapLoaded = false; // Ocultamos el mapa antes de cambiarlo
    this.selectedMap = idmap;
  }

}
