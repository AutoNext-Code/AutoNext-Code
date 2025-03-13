import { Component } from '@angular/core';

@Component({
  selector: 'app-maps',
  imports: [],
  templateUrl: './maps.component.html',
  styleUrl: './maps.component.css'
})
export class MapsComponent {
  

  //COLORES DE LOS COCHES, SE APLICAN CON UN FILTRO
  colors = {
    available: 'invert(20%) sepia(81%) saturate(6120%) hue-rotate(120deg) brightness(96%) contrast(102%)' ,
    occupied:  'invert(18%) sepia(90%) saturate(5946%) hue-rotate(357deg) brightness(121%) contrast(127%)' ,
    blocked:   'invert(0%) sepia(7%) saturate(0%) hue-rotate(126deg) brightness(94%) contrast(106%)' ,
    unusable:  'invert(55%) sepia(3%) saturate(31%) hue-rotate(339deg) brightness(91%) contrast(81%)' ,
  }


  //LISTA DE LOS PUNTOS
  points = [
    { x: 20, y: 140, label: 'Punto 1', color: this.colors.available },
    { x: 60, y: 140, label: 'Punto 1', color: this.colors.available },
    { x: 90, y: 140, label: 'Punto 1', color: this.colors.available },
    { x: 125, y: 140, label: 'Punto 1', color: this.colors.available },
    { x: 155, y: 140, label: 'Punto 1', color: this.colors.available },
    { x: 20, y: 140, label: 'Punto 1', color: this.colors.available },
    { x: 400, y: 300, label: 'Punto 2', color: this.colors.blocked },
  ];

  onPointClick(point: any): void {
    alert(`Hiciste clic en: ${point.label}`);
  }

}
