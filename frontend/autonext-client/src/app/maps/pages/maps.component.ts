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
import { NgIf } from '@angular/common';
@Component({
  selector: 'app-maps',
  imports: [SpaceDataComponent],
  templateUrl: './maps.component.html',
  styleUrl: './maps.component.css',
})
export class MapsComponent implements OnInit {

  imageUrl: String = '';
  spaces: Space[] = [];
  unusable = State.Unusable;

  formData!: SpaceData ;

  @Input() chart: any;
  @Input() plugType: PlugType = PlugType.Undefined;
  @Output() mapLoaded = new EventEmitter<boolean>();
  @ViewChild('svgElement') svgElement!: ElementRef;

  variable:boolean = true;

  isLoaded: boolean = false;
  modal: boolean = true;
  carData!: SpaceData;

  public dataRequestService = inject(DataRequestService) ;

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

    let number:number = 0;
    if (typeof this.plugType === 'string') {
      number = PlugType[this.plugType as keyof typeof PlugType];
    }


    return ((space.plugType === this.plugType) || (number==PlugType.Undefined)) ? space.state : State.Unusable;
  }

  statusSpace(space: Space): boolean{
    let number:number = 0;
    if (typeof this.plugType === 'string') {
      number = PlugType[this.plugType as keyof typeof PlugType];
    }


    return ((space.plugType === this.plugType) || (number==PlugType.Undefined));
  }

  spaceNotTaken(space:Space):boolean{

    return ((space.state== State.Occupied)||(space.state==State.Own_Reservation));
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

  toggleModal(spaceId: number): void {
    this.carData = {...this.dataRequestService.getData(), parkingSpaceId: spaceId};
    this.modal = false;
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
