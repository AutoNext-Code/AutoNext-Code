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

import { Space } from '@maps/interfaces/Space.interface';
import { Direction } from '@maps/enums/Direction.enum';
import { State } from '@maps/enums/State.enum';

import { SpaceDataComponent } from '@booking/components/space-data/space-data.component';
import { SpaceData } from '@booking/interfaces/spaceData.interface';
@Component({
  selector: 'app-maps',
  imports: [SpaceDataComponent],
  templateUrl: './maps.component.html',
  styleUrl: './maps.component.css',
})
export class MapsComponent implements OnInit {

  imageUrl: String = '';
  spaces: Space[] = [];

  @Input() chart: any;
  @Input() mapSelected: number = 1;
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

    if(this.chart){

      this.imageUrl = this.chart.imageUrl;

      this.spaces = this.chart.spaces.map((space: any) => ({
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
    }

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

  stateColorMap: Record<string, string> = {
    Available: 'var(--green)',
    Occupied: 'var(--red)',
    Blocked: 'var(--gray)',
    Own_Reservation: 'var(--light-blue)',
    Unusable: 'var(--light-gray)',
  };


}
