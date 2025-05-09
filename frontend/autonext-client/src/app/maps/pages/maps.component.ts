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
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { State } from '@maps/enums/state.enum';
import { PlugType } from '@maps/enums/plugType.enum';
import { Direction } from '@maps/enums/direction.enum';
import { MapService } from '@maps/services/map.service';
import { Space } from '@maps/interfaces/Space.interface';
import { CanBookResponse } from '@maps/interfaces/CanBookResponse';
import { DataRequestService } from '@maps/services/data-request.service';
import { BookingListService } from '@maps/services/booking-list.service';
import { BookingList } from '@maps/interfaces/bookingList.interface';
import { EditingSpaceService } from '@maps/services/editingSpace.service';

import { JobPosition } from '@admin/enums/jobPosition.enum';

import { SpaceDataComponent } from '@booking/components/space-data/space-data.component';
import { SpaceData } from '@booking/interfaces/spaceData.interface';

import { AuthService } from '@auth/services/auth.service';

import { CustomModalComponent } from '@shared/components/custom-modal/custom-modal.component';
import { CustomButtonComponent } from '@shared/components/ui/custom-button/custom-button.component';

import { AppComponent } from '../../app.component';

import { BookingListComponent } from '../components/booking-list/booking-list.component';

import { Observable } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { AdminService } from '@admin/services/admin.service';

@Component({
  selector: 'app-maps',
  imports: [
    SpaceDataComponent,
    CommonModule,
    CustomModalComponent,
    CustomButtonComponent,
    FormsModule,
    BookingListComponent,
  ],
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
  @Input({ required: true }) adminView: boolean = false;
  @Input() plugType: PlugType = PlugType.Undefined;
  @Input() noType: PlugType = PlugType.NoType;

  @Output() mapLoaded = new EventEmitter<boolean>();

  @ViewChild('svgElement') svgElement!: ElementRef;

  userCanBook?: Observable<CanBookResponse>;
  isLoaded: boolean = false;
  canBook: CanBookResponse = { message: '' };
  modal: boolean = true;
  edit: boolean = false;
  adminModal: boolean = false;
  bookingList!: BookingList;
  spaceEditedId!: number;
  jobPosition: number = 0;
  carData!: SpaceData;

  editPlugType!: number;

  public blockButtonColor: 'red' | 'green' = 'red';
  public blockButtonLabel: 'Bloquear' | 'Desbloquear' = 'Bloquear';
  public blockButtonIcon: 'shield' | 'unlock' = 'shield';

  private appComponent: AppComponent = inject(AppComponent);
  private editingSpaceService: EditingSpaceService =
    inject(EditingSpaceService);
  private bookingListService: BookingListService = inject(BookingListService);
  public dataRequestService = inject(DataRequestService);
  private adminService = inject(AdminService);
  private mapService = inject(MapService);
  private authService = inject(AuthService);
  private appComponente = inject(AppComponent);

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

  spaceTaken(space: Space): boolean {
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

    if (this.authService.isUserPenalized()) {
      this.appComponente.showToast(
        'error',
        'Acceso restringido',
        'No puedes realizar reservas porque estás penalizado actualmente.',
        1600
      );
      return;
    }

    this.carData = {
      ...this.dataRequestService.getData(),
      parkingSpaceId: spaceId,
      plugType: plugType,
    };

    this.userCanBook = this.mapService.checkUserCanBook(
      this.carData.date,
      this.carData.startTime,
      this.carData.endTime
    );

    this.userCanBook.subscribe({
      next: (data) => {
        this.canBook = data;
      },
      error: (error) => {
        console.error('Error al verificar la disponibilidad:', error);
      },
    });

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

  closeAdmin(): void {
    this.edit = false;
    this.adminModal = false;
  }

  toggleAdmin(space: Space): void {
    this.spaceEditedId = space.id;
    this.editPlugType = (PlugType as any)[space.plugType];
    this.adminModal = true;
    this.loadPastBookings(1);
  }

  toggleEdit(): void {
    this.edit = true;
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

    if (this.adminView) {
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

  private reloadSpacesFromChart(): void {
    if (!this.chart) return;

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
  }

  loadPastBookings(page: number) {
    this.bookingListService
      .getAllBookingBySpace(this.spaceEditedId, page)
      .subscribe({
        next: (listData) => {
          this.bookingList = listData;
        },
        error: (err) => {
          console.error('Error while loading map:', err);
        },
      });
  }

  getTooltipText(space: Space): string {
    if (space.state === State.Own_Reservation) {
      return 'Reserva de ' + space.startTime + ' a ' + space.endTime;
    } else if (space.state === State.Occupied) {
      return 'Ocupado de ' + space.startTime + ' a ' + space.endTime;
    } else {
      return 'Bloqueado por el sistema';
    }
  }

  updateSpace(id: number, jobPosition: JobPosition): void {
    console.log(this.editPlugType);
  
    this.editingSpaceService
      .spaceEdit(id, PlugType[this.editPlugType], jobPosition)
      .subscribe({
        next: (response) => {
          this.appComponent.showToast('success', response, '');
        },
        error: (error: HttpErrorResponse) => {
          this.appComponent.showToast('error', error.message, '');
        },
      });
  }
  

  spaceNoType(plugType: PlugType): boolean {
    let number: number = 0;

    if (typeof plugType === 'string') {
      number = PlugType[plugType as keyof typeof PlugType];
    }

    return number === PlugType.NoType;
  }

  updateSpaceState(id: number): void {
    this.adminService.updateSpaceState(id).subscribe({
      next: (response) => {
        this.appComponent.showToast('success', response, '');

        if (this.adminView) {
          const space = this.getSpaceById(id);
          if (space) {
            space.state = space.state === State.Blocked ? State.Available : State.Blocked;

            // 🔁 Actualiza propiedades visuales para el botón
            const isNowBlocked = space.state === State.Blocked;
            this.blockButtonColor = isNowBlocked ? 'green' : 'red';
            this.blockButtonLabel = isNowBlocked ? 'Desbloquear' : 'Bloquear';
            this.blockButtonIcon = isNowBlocked ? 'unlock' : 'shield';
          }
        } else {
          this.refreshMap();
        }
      },
      error: (error: HttpErrorResponse) => {
        this.appComponent.showToast('error', error.message, '');
      },
    });
  }


  getSpaceById(id: number): Space | undefined {
    return this.spaces.find((space) => space.id === id);
  }

  get isBlockable(): boolean {
    const space = this.getSpaceById(this.spaceEditedId);
    if (!space) return false;

    const plugTypeValue =
      typeof space.plugType === 'string'
        ? PlugType[space.plugType as keyof typeof PlugType]
        : space.plugType;

    const isValid = plugTypeValue !== PlugType.NoType;

    return isValid;
  }

  get isBlocked(): boolean {
    const space = this.getSpaceById(this.spaceEditedId);
    if (!space) return false;
    return space.state === State.Blocked;
  }

  isMobile(): boolean {
    return window.innerWidth < 1024;
  }
  

}
