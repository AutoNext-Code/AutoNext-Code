import {
  Component,
  ElementRef,
  EventEmitter,
  inject,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { Slot } from '../types/slot.type';
import { MapLoaderService } from '@maps/services/map.service';
import { Chart } from '@maps/interfaces/Chart.interface';
import { Observable } from 'rxjs';
import { Space } from '@maps/interfaces/Space.interface';
import { Direction } from '@maps/enums/Direction.enum';
import { State } from '@maps/enums/State.enum';

import { SpaceDataComponent } from '@booking/components/space-data/space-data.component';
import { SpaceData } from '@booking/interfaces/spaceData.interface';

import { CustomButtonComponent } from '../../shared/components/ui/custom-button/custom-button.component';

@Component({
  selector: 'app-maps',
  imports: [SpaceDataComponent, CustomButtonComponent],
  templateUrl: './maps.component.html',
  styleUrl: './maps.component.css',
})
export class MapsComponent implements OnInit {
  private mapLoader: MapLoaderService = inject(MapLoaderService);
  chart: any;
  imageUrl: String = '';
  spaces: Space[] = [];

  @Input() mapSelected: number = 0;
  @Output() mapLoaded = new EventEmitter<boolean>();
  @ViewChild('svgElement') svgElement!: ElementRef;

  isLoaded: boolean = false;
  modal: boolean = true;
  carData!: SpaceData;

  ngOnInit(): void {
    this.isLoaded = false;
  }

  ngAfterViewInit(): void {
    this.checkImageLoad();
  }

  ngOnChanges(): void {
    this.isLoaded = false;
    this.chartLoad();
  }

  chartLoad() {
    this.mapLoader.mapLoad(this.mapSelected).subscribe((response) => {
      this.chart = response;
      this.imageUrl = response.imageUrl;

      this.spaces = response.spaces.map((space: any) => ({
        ...space,
        direction:
          typeof space.direction === 'string'
            ? Direction[space.direction as keyof typeof Direction]
            : space.direction,
        state:
          typeof space.state === 'string'
            ? State[space.state as keyof typeof State]
            : space.state,
      }));

      this.checkImageLoad();
    });
  }

  checkImageLoad() {
    const image = new Image();
    image.src = this.imageUrl.toString();

    image.onload = () => {
      this.isLoaded = true;
      this.mapLoaded.emit(true);
    };
  }

  toggleModal(carData: SpaceData) {
    this.modal = false;
    this.carData = carData;
  }

  closeModal() {
    this.modal = true;
  }
}
