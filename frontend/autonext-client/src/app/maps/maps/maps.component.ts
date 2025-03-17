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
    available: 'invert(20%) sepia(81%) saturate(6120%) hue-rotate(120deg) brightness(96%)  contrast(102%)' ,
    occupied:  'invert(18%) sepia(90%) saturate(5946%) hue-rotate(357deg) brightness(121%) contrast(127%)' ,
    blocked:   'invert(0%)  sepia(7%)  saturate(0%)    hue-rotate(126deg) brightness(94%)  contrast(106%)' ,
    unusable:  'invert(55%) sepia(3%)  saturate(31%)   hue-rotate(339deg) brightness(91%)  contrast(81%)' ,
  }

  direcciones = {
    arriba: 0,
    abajo: 180,
    derecha: 90,
    izquierda: 270
  }

  //LISTA DE LOS PUNTOS
  points = [

    { x: 30,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 60,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 90,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 125, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 155, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 190, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 225, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 255, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 290, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 325, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 355, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 390, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 425, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 455, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 490, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 525, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 555, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 585, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 620, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 650, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 690, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 720, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 750, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 790, y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

    { x: 155, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 190, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 225, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 255, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 290, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 325, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 355, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 390, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 425, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 455, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 490, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 525, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 555, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 585, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 620, y: 270, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

    { x: 740, y: 260, label: '', color: this.colors.available, direccion: this.direcciones.izquierda },
    { x: 740, y: 290, label: '', color: this.colors.available, direccion: this.direcciones.izquierda },
    { x: 740, y: 320, label: '', color: this.colors.available, direccion: this.direcciones.izquierda },
    { x: 740, y: 350, label: '', color: this.colors.available, direccion: this.direcciones.izquierda },

    { x: 155, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 190, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 225, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 255, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 290, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 325, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 355, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 390, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 425, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 455, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 490, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 525, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 555, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 585, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
    { x: 620, y: 330, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

    { x: 30,  y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 60,  y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 90,  y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 125, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 155, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 190, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 225, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 255, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 290, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 325, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 355, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 390, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 425, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 455, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 490, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 525, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 555, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 585, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 620, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 650, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 690, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
    { x: 720, y: 470, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
  ];

  onPointClick(point: any): void {
    alert(`Hiciste clic en: ${point.label}`);
  }

}
