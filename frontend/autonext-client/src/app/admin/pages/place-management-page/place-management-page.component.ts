import { Component, inject } from '@angular/core';

import { MapsComponent } from "@maps/pages/maps.component";
import { MapService } from '@maps/services/map.service';

import { AdminMapFormComponent } from "./components/admin-map-form/admin-map-form.component";

@Component({
  imports: [MapsComponent, AdminMapFormComponent],
  templateUrl: './place-management-page.component.html',
  styleUrl: './place-management-page.component.css'
})
export class PlaceManagementPageComponent {

  private mapService: MapService = inject(MapService);

  selectedMap: number = 1 ;
  chart:any ;

  changeMap(newMap: number) {
    this.selectedMap = newMap ;
    this.chartLoad();
  }

  chartLoad() {

    if(this.selectedMap){
      this.mapService.formAdminMapById(this.selectedMap).subscribe((response) => {
        this.chart = response;
      });
    }
  }

  adminChartLoad(){
    if(this.selectedMap){
      this.mapService.formAdminMapById(this.selectedMap).subscribe((response) => {
        this.chart = response;
      });
    }
  }
}
