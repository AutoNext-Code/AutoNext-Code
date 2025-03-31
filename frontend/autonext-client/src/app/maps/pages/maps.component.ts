import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Slot } from '../types/slot.type';

@Component({
  selector: 'app-maps',
  imports: [],
  templateUrl: './maps.component.html',
  styleUrl: './maps.component.css'
})
export class MapsComponent implements OnInit {

  @Input() mapSelected: string = '';
  @Output() mapLoaded = new EventEmitter<boolean>(); // Evento para avisar que el mapa está listo
  @ViewChild('svgElement') svgElement!: ElementRef; // Referencia al SVG
  
  isLoaded = false;

  ngOnInit(): void {
    console.log('Mapa seleccionado:', this.mapSelected);
    this.isLoaded = false; // Reseteamos el estado al cambiar de mapa
  }

  ngAfterViewInit(): void {
    this.checkImageLoad();
  }

  ngOnChanges(): void {
    this.isLoaded = false; // Se oculta cada vez que cambia el mapa
    this.checkImageLoad();
  }

  checkImageLoad() {
    const image = new Image();
    image.src = `./assets/img/mapas/${this.mapSelected}.png`;

    image.onload = () => {
      this.isLoaded = true;
      this.mapLoaded.emit(true); // Emitimos que el mapa está cargado
    };
  }
  colors = {
    available: 'invert(20%) sepia(81%) saturate(6120%) hue-rotate(120deg) brightness(96%) contrast(102%)',
    occupied: 'invert(18%) sepia(90%) saturate(5946%) hue-rotate(357deg) brightness(121%) contrast(127%)',
    blocked: 'invert(0%) sepia(7%) saturate(0%) hue-rotate(126deg) brightness(94%) contrast(106%)',
    unusable: 'invert(55%) sepia(3%) saturate(31%) hue-rotate(339deg) brightness(91%) contrast(81%)',
  };


  direcciones = {
    arriba: 0,
    abajo: 180,
    derecha: 90,
    izquierda: 270
  };
  

  points: { [key: string]: Slot[] } = {
    'Madrid-1': [

        /* Arriba */

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

        /* Centro Arriba */

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

        /* Derecha */

        { x: 740, y: 260, label: '', color: this.colors.available, direccion: this.direcciones.izquierda },
        { x: 740, y: 290, label: '', color: this.colors.available, direccion: this.direcciones.izquierda },
        { x: 740, y: 320, label: '', color: this.colors.available, direccion: this.direcciones.izquierda },
        { x: 740, y: 350, label: '', color: this.colors.available, direccion: this.direcciones.izquierda },

        /* Centro Abajo */

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

        /* Abajo */

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
      ],

      'Madrid-2': [

      /* Arriba */

      { x: 180,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
      { x: 210,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

      { x: 245,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
      { x: 275,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

      { x: 310,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
      { x: 340,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

      { x: 375,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
      { x: 405,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

      { x: 445,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
      { x: 475,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

      { x: 510,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
      { x: 540,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

      { x: 575,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
      { x: 605,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

      { x: 640,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
      { x: 670,  y: 160, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

    /* Izquierda */

      { x: 130,  y: 230, label: '', color: this.colors.available, direccion: this.direcciones.derecha },
      { x: 130,  y: 260, label: '', color: this.colors.available, direccion: this.direcciones.derecha },

      { x: 130,  y: 295, label: '', color: this.colors.available, direccion: this.direcciones.derecha },
      { x: 130,  y: 325, label: '', color: this.colors.available, direccion: this.direcciones.derecha },

      { x: 130,  y: 360, label: '', color: this.colors.available, direccion: this.direcciones.derecha },
      { x: 130,  y: 390, label: '', color: this.colors.available, direccion: this.direcciones.derecha },

    /* Centro */

      { x: 275,  y: 310, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
      { x: 310,  y: 310, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      { x: 375,  y: 310, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
      { x: 405,  y: 310, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      { x: 507,  y: 310, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
      { x: 535,  y: 310, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      { x: 575,  y: 310, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
      { x: 605,  y: 310, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

    /* Abajo */

      { x: 180,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
      { x: 210,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      { x: 245,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
      { x: 275,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      { x: 310,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
      { x: 340,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      { x: 375,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
      { x: 405,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      { x: 445,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
      { x: 475,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      { x: 510,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
      { x: 540,  y: 460, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      ],

      'Madrid-3': [
        /* Arriba */

        { x: 135,  y: 130, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 170,  y: 130, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 203,  y: 130, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

        { x: 240,  y: 130, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 275,  y: 130, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 304,  y: 130, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

        { x: 340,  y: 130, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 375,  y: 130, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

      /* Centro Izquierda Arriba */

        { x: 135,  y: 275, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 170,  y: 275, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

        { x: 215,  y: 275, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 250,  y: 275, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

        { x: 315,  y: 275, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 345,  y: 275, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

        { x: 385,  y: 275, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 420,  y: 275, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      /* Centro Izquierda Abajo */

        { x: 135,  y: 345, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 170,  y: 345, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

        { x: 385,  y: 345, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 420,  y: 345, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

      /* Centro Derecha */

        { x: 550,  y: 265, label: '', color: this.colors.available, direccion: this.direcciones.izquierda },
        { x: 550,  y: 295, label: '', color: this.colors.available, direccion: this.direcciones.izquierda },

        { x: 540,  y: 345, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 575,  y: 345, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

      /* Abajo */


        { x: 135,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 170,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

        { x: 215,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 250,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

        { x: 310,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 345,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

        { x: 385,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 420,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

        { x: 455,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 495,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

        { x: 540,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 575,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

        { x: 615,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 655,  y: 500, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      ],

      'Málaga-0': [

      /* Arriba */

        { x: 160,  y: 200, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 201,  y: 200, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 239,  y: 200, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 277,  y: 200, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 315,  y: 200, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 355,  y: 200, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 394,  y: 200, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 433,  y: 200, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

        { x: 586,  y: 200, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 625,  y: 200, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 665,  y: 200, label: '', color: this.colors.available, direccion: this.direcciones.abajo },


      /* Abajo */

        { x: 160,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 201,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 239,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 277,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 315,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 355,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

        { x: 467,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 506,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 546,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 586,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 625,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 665,  y: 370, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      ],

      'Málaga-1': [

      /* Arriba */

        { x: 75,   y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 110,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 147,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 185,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 222,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 256,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 293,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 330,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 365,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 400,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 437,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 474,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 510,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 545,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 583,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 617,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 655,  y: 140, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

    /* Centro Arriba */

        { x: 75,   y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 110,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 147,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 185,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 222,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 256,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 293,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 330,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 365,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 400,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 437,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 474,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 510,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 545,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 583,  y: 280, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

    /* Centro Abajo */

        { x: 75,   y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 110,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 147,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 185,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 222,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 256,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 293,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 330,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 365,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 400,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 437,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 474,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 510,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 545,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },
        { x: 583,  y: 350, label: '', color: this.colors.available, direccion: this.direcciones.abajo },

    /* Abajo */


        { x: 110,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 147,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 185,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 222,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 260,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 297,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 333,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 370,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 407,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 444,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 480,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 517,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },
        { x: 552,  y: 490, label: '', color: this.colors.available, direccion: this.direcciones.arriba },

      ]


    };

}
