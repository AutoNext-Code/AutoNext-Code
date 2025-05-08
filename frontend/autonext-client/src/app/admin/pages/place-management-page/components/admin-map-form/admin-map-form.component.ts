import { Component, EventEmitter, Output } from '@angular/core';

import { SelectDepComponent } from "@shared/components/ui/select-dep/select-dep.component";

@Component({
  selector: 'admin-map-form',
  imports: [
    SelectDepComponent
  ],
  templateUrl: './admin-map-form.component.html',
  styleUrl: './admin-map-form.component.css'
})
export class AdminMapFormComponent {

  @Output()
  mapEmitter: EventEmitter<number> = new EventEmitter() ;

  ciudades = ["Madrid", "Malaga"];
  plantasCiudad = {
    "Madrid": [
        {number: '1', id: 1},
        {number: '2', id: 2},
        {number: '3', id: 3},
      ],
    "Malaga": [
        {number: '0', id: 4},
        {number: '1', id: 5},
    ],
  };

  mapSelected: string = '';

  updateMap(id: number) {
    this.mapEmitter.emit(id) ;
  }

}
