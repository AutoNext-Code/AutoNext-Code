import { Component } from '@angular/core';
import { HeaderComponent } from '@shared/header/header.component';
import { MapsComponent } from '@maps/pages/maps.component';
import { BookingFormComponent } from '@user/components/booking-form/booking-form.component';
import { LoaderComponent } from "../../../shared/loader/loader.component";

@Component({
  imports: [HeaderComponent, MapsComponent, BookingFormComponent, LoaderComponent],
  templateUrl: './home-user-page.component.html',
  styleUrl: './home-user-page.component.css'
})
export class HomeUserPageComponent {

  selectedMap: string = '';  // Para guardar el mapa seleccionado
  isMapLoaded = false;       // Para manejar la carga del mapa

  updateSelectedMap(map: string) {
    this.isMapLoaded = false; // Ocultamos el mapa antes de cambiarlo
    this.selectedMap = map;
  }

}