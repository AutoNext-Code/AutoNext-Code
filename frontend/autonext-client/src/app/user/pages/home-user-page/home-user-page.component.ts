import { PlugType } from '@maps/enums/plugType.enum';
import { Component, inject, OnInit } from '@angular/core';

import { MapsComponent } from '@maps/pages/maps.component';

import { BookingFormComponent } from '@maps/components/booking-form/booking-form.component';

import { HeaderComponent } from '@shared/header/header.component';
import { LoaderComponent } from "@shared/loader/loader.component";
import { MapService } from '@maps/services/map.service';
import { CentersMaps } from '@maps/interfaces/CentersMaps.interface';
import { MapParams } from '@maps/interfaces/MapParams.interface';
import { FormValues } from '@maps/interfaces/FormValues.interface';


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
  formValues!: FormValues;
  plugType: PlugType= 1;


  updateSelectedMap(formValues: FormValues) {
    this.formValues = formValues;
    this.plugType=formValues.plugtype;
    this.isMapLoaded = false; // Ocultamos el mapa antes de cambiarlo

    this.chartLoad();
  }

  chartLoad() {

    if(this.formValues.mapId){
      const {mapId, date, startTime, endTime} = this.formValues
      const mapParams:MapParams={mapId, date, startTime, endTime};

      this.mapService.formMapLoad(mapParams).subscribe((response) => {
        this.chart = response;
      });
    }
  }



  ngOnInit(): void {
    this.mapService.centersMaps$.subscribe(data =>{this.maps = data})



    this.mapService.centersLevelsLoad();

  }

}
