import { Component, inject, OnInit } from '@angular/core';

import { MapsComponent } from '@maps/pages/maps.component';

import { BookingFormComponent } from '@maps/components/booking-form/booking-form.component';

import { HeaderComponent } from '@shared/header/header.component';
import { LoaderComponent } from "@shared/loader/loader.component";
import { MapService } from '@user/services/map.service';
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
  selectedMap!: number;
  chart:any;
  isMapLoaded = false;
  date:string = "2025-04-04";
  plugType:number = 1;
  startTime:string = "09:00";
  endTime:string = "11:00";


  updateSelectedMap(mapId: number) {
    this.isMapLoaded = false; // Ocultamos el mapa antes de cambiarlo
    this.selectedMap = mapId;
    this.chartLoad();
  }

  chartLoad() {

    if(this.selectedMap){
      this.mapService.formMapLoad(this.selectedMap, this.date, this.plugType, this.startTime, this.endTime).subscribe((response) => {
        this.chart = response;
      });
    }
  }



  ngOnInit(): void {
    this.mapService.centersMaps$.subscribe(data =>{this.maps = data})


    this.mapService.centersLevelsLoad();

  }

}
