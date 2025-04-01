import { Component, ElementRef, EventEmitter, inject, Input, OnInit, Output, ViewChild } from '@angular/core';
import { Slot } from '../types/slot.type';
import { MapLoaderService } from '@maps/services/map-loader.service';
import { Chart } from '@maps/interfaces/Chart.interface';
import { Observable } from 'rxjs';
import { Space } from '@maps/interfaces/Space.interface';
import { Direction } from '@maps/enums/Direction.enum';
import { State } from '@maps/enums/State.enum';


@Component({
  selector: 'app-maps',
  imports: [],
  templateUrl: './maps.component.html',
  styleUrl: './maps.component.css'
})
export class MapsComponent implements OnInit {

  private mapLoader: MapLoaderService = inject(MapLoaderService);
  chart: any;
  imageUrl: String = "";
  spaces:Space[] = [];


  @Input() mapSelected: string = '';
  @Output() mapLoaded = new EventEmitter<boolean>(); // Evento para avisar que el mapa está listo
  @ViewChild('svgElement') svgElement!: ElementRef; // Referencia al SVG

  isLoaded = false;

  ngOnInit(): void {
    this.chartLoad();
    this.checkImageLoad
    console.log('Mapa seleccionado:', this.mapSelected);
    this.isLoaded = false; // Reseteamos el estado al cambiar de mapa
  }

  ngAfterViewInit(): void {
    this.checkImageLoad();
  }

  ngOnChanges(): void {
    this.isLoaded = false; 
    this.checkImageLoad();
    this.chartLoad();

  }

  chartLoad() {
    this.mapLoader.mapLoad(this.mapSelected).subscribe((response) => {
      this.chart = response;
      this.imageUrl = response.imageUrl;
  
      this.spaces = response.spaces.map((space: any) => ({
        ...space,
        direction: typeof space.direction === 'string'
          ? Direction[space.direction as keyof typeof Direction]
          : space.direction,
        state: typeof space.state === 'string'
          ? State[space.state as keyof typeof State]
          : space.state
      }));
    });
  }
  

  checkImageLoad() {

    this.chartLoad();

    const image = new Image();
    image.src = this.imageUrl.toString();


    image.onload = () => {
      this.isLoaded = true;
      this.mapLoaded.emit(true); // Emitimos que el mapa está cargado
    };
  }

}
