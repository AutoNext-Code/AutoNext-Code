import { Component, inject, OnInit } from '@angular/core';

import { MapsComponent } from '@maps/pages/maps.component';

import { BookingFormComponent } from '@maps/components/booking-form/booking-form.component';

import { HeaderComponent } from '@shared/header/header.component';
import { LoaderComponent } from "@shared/loader/loader.component";
import { MapService } from '@maps/services/map.service';
import { CentersMaps } from '@maps/interfaces/CentersMaps.interface';
import { CenterLevel } from '../interfaces/CenterLevel.interface';

@Component({
  imports: [HeaderComponent, MapsComponent, BookingFormComponent, LoaderComponent],
  templateUrl: './home-user-page.component.html',
  styleUrl: './home-user-page.component.css'
})
export class HomeUserPageComponent implements OnInit{


  private mapService: MapService = inject(MapService);

  maps:CentersMaps[] = [];


  selectedMap!: number;  // Para guardar el mapa seleccionado
  isMapLoaded = false;       // Para manejar la carga del mapa

  updateSelectedMap(mapId: number) {
    this.isMapLoaded = false; // Ocultamos el mapa antes de cambiarlo
    this.selectedMap = mapId;
  }

  updateCenterLevels(){


  }

  ngOnInit(): void {
    this.mapService.centersMaps$.subscribe(data =>{this.maps = data})


    this.mapService.centersLevelsLoad();

  }

}
