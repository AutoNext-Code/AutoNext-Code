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
import { Direction } from '@maps/enums/direction.enum';
import { State } from '@maps/enums/state.enum';
import { DataRequestService } from '@maps/services/data-request.service';

import { SpaceDataComponent } from '@booking/components/space-data/space-data.component';
import { SpaceData } from '@booking/interfaces/spaceData.interface';
import { PlugType } from '@maps/enums/plugType.enum';
import { CommonModule } from '@angular/common';
import { MapService } from '@maps/services/map.service';
@Component({
  selector: 'app-maps',
  imports: [SpaceDataComponent, CommonModule],
  templateUrl: './maps.component.html',
  styleUrl: './maps.component.css',
})
export class MapsComponent implements OnInit {
  imageUrl: String = '';
  spaces: Space[] = [];
  unusable = State.Unusable;

  formData!: SpaceData;

  private justClosed = false;

  @Input() chart: any;
  @Input() plugType: PlugType = PlugType.Undefined;

  @Output() mapLoaded = new EventEmitter<boolean>();

  @ViewChild('svgElement') svgElement!: ElementRef;

  variable: boolean = true;

  isLoaded: boolean = false;
  modal: boolean = true;
  carData!: SpaceData;

  public dataRequestService = inject(DataRequestService);
  private mapService = inject(MapService);

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

  plugTypeFilter(space: Space) {
    let number: number = 0;
    if (typeof this.plugType === 'string') {
      number = PlugType[this.plugType as keyof typeof PlugType];
    }

    return space.plugType === this.plugType || number == PlugType.Undefined
      ? space.state
      : State.Unusable;
  }

  statusSpace(space: Space): boolean {
    let number: number = 0;
    if (typeof this.plugType === 'string') {
      number = PlugType[this.plugType as keyof typeof PlugType];
    }

    return space.plugType === this.plugType || number == PlugType.Undefined;
  }

  spaceNotTaken(space: Space): boolean {
    return (
      space.state == State.Occupied ||
      space.state == State.Own_Reservation ||
      space.state == State.Blocked
    );
  }

  chartLoad() {
    if (this.chart) {
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

  toggleModal(spaceId: number, plugType: string): void {
    if (this.justClosed) {
      return;
    }

    this.carData = {
      ...this.dataRequestService.getData(),
      parkingSpaceId: spaceId,
      plugType: plugType,
    };

    this.modal = false;
  }

  closeModal(): void {
    this.modal = true;
    this.carData = null!;

    setTimeout(() => {
      this.justClosed = false;
    }, 300);

    this.refreshMap();
  }

  stateColorMap: Record<string, string> = {
    Available: 'var(--green)',
    Occupied: 'var(--red)',
    Blocked: 'var(--gray)',
    Own_Reservation: 'var(--light-blue)',
    Unusable: 'var(--light-gray)',
  };

  refreshMap() {
    if (!this.chart) {
      return;
    }

    const mapParams = {
      mapId: this.chart.id,
      date: this.dataRequestService.getData().date,
      startTime: this.dataRequestService.getData().startTime,
      endTime: this.dataRequestService.getData().endTime,
    };

    this.mapService.formMapLoad(mapParams).subscribe({
      next: (chartData) => {
        this.mapService.maps$.next(chartData);
        this.chart = chartData;
        this.chartLoad();
      },
      error: (err) => {
        console.error('[Map] Error while refreshing map:', err);
      },
    });
  }
}