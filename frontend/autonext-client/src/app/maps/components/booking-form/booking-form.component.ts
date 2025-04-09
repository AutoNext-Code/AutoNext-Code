import {
  Component,
  EventEmitter,
  inject,
  Output,
  OnInit,
  OnChanges,
  Input,
  SimpleChanges,
  ViewChild,
  ElementRef,
  AfterViewInit,
} from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { CarDto } from '@user/interfaces/car.interface';
import { CarService } from '@user/services/car.service';
import { CenterLevel } from '@user/interfaces/CenterLevel.interface';

import { PlugType } from '@maps/enums/plugType.enum';
import { DataRequestService } from '@maps/services/data-request.service';
import {
  CentersMaps,
  ParkingLevel,
} from '@maps/interfaces/CentersMaps.interface';

import { FormValues } from '@maps/interfaces/FormValues.interface';
import { SpaceData } from '@booking/interfaces/spaceData.interface';
import { AppComponent } from '../../../app.component';
import { debounceTime } from 'rxjs';

@Component({
  selector: 'user-booking-form',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.css'],
})
export class BookingFormComponent implements OnInit, OnChanges {
  private carService: CarService = inject(CarService);
  private dataRequestService: DataRequestService = inject(DataRequestService);
  private appComponent: AppComponent = inject(AppComponent);

  @ViewChild('levelSelect') level!: ElementRef<HTMLSelectElement>;

  public plugTypes: (string | PlugType)[];
  public selectedPlugType: string;
  public selectedPlugTypeValue: number | null = null;
  public cars: CarDto[] = [];

  public levelString!: string;

  @Input() maps: CentersMaps[] = [];
  selectedCenter!: string;
  selectedLevel!: number;
  parkingLevels: ParkingLevel[] = [];
  CenterLevel?: CenterLevel;

  myForm!: FormGroup;

  public actualStartDate = new Date();
  public actualEndHour = new Date(
    this.actualStartDate.getTime() + 30 * 60 * 1000
  );

  startHours: string[] = [
    '08:00',
    '08:30',
    '09:00',
    '09:30',
    '10:00',
    '10:30',
    '11:00',
    '11:30',
    '12:00',
    '12:30',
    '13:00',
    '13:30',
    '14:00',
    '14:30',
    '15:00',
    '15:30',
    '16:00',
    '16:30',
    '17:00',
    '17:30',
    '18:00',
    '18:30',
    '19:00',
    '19:30',
  ];

  endHours: string[] = [
    '08:30',
    '09:00',
    '09:30',
    '10:00',
    '10:30',
    '11:00',
    '11:30',
    '12:00',
    '12:30',
    '13:00',
    '13:30',
    '14:00',
    '14:30',
    '15:00',
    '15:30',
    '16:00',
    '16:30',
    '17:00',
    '17:30',
    '18:00',
    '18:30',
    '19:00',
    '19:30',
    '20:00',
  ];

  filteredStartHours: string[] = [];
  filteredEndHours: string[] = [];

  @Output() filterChanged: EventEmitter<FormValues> =
    new EventEmitter<FormValues>();

  constructor() {
    this.myForm = new FormGroup({
      center: new FormControl(''),
      level: new FormControl(''),
      selectedCar: new FormControl(null),
      selectedPlugType: new FormControl(),
      date: new FormControl(this.getDate()),
      startHour: new FormControl(
        this.roundToNearestHalfHour(this.actualStartDate)
      ),
      endHour: new FormControl(this.roundToNearestHalfHour(this.actualEndHour)),
    });

    this.selectedPlugType = 'Undefined';

    this.myForm.valueChanges.pipe(debounceTime(0)).subscribe(() => {
      this.validateHourRange();
      this.setData();
    });

    this.myForm.get('center')?.valueChanges.subscribe((value) => {
      this.updateParkingLevels(value);
      this.filterChanged.emit(this.getFilterValues());

      setTimeout(() => {
        const nativeSelect = this.level.nativeElement;
        const selectedOption = nativeSelect.options[nativeSelect.selectedIndex];
        this.levelString = selectedOption ? selectedOption.text : '0';
      }, 0);
    });

    this.myForm.get('level')?.valueChanges.subscribe((value) => {
      setTimeout(() => {
        const nativeSelect = this.level.nativeElement;
        const selectedOption = nativeSelect.options[nativeSelect.selectedIndex];
        const levelRef: string = selectedOption ? selectedOption.text : '';

        this.levelString = levelRef;

        this.filterChanged.emit(value);
        this.filterChanged.emit(this.getFilterValues());
      }, 0);
    });

    this.myForm.get('selectedCar')?.valueChanges.subscribe(() => {
      this.updatePlugTypes();
      this.filterChanged.emit(this.getFilterValues());
    });

    this.myForm.get('date')?.valueChanges.subscribe((selectedDate: string) => {
      if (!selectedDate) return;

      const today = this.getDate(0);
      const selected = new Date(selectedDate).toISOString().split('T')[0];

      if (selected === today) {
        const currentTime = this.roundToNearestHalfHour(new Date());
        this.filteredStartHours = this.startHours.filter(
          (hour) => hour >= currentTime
        );
        this.filteredEndHours = this.endHours.filter(
          (hour) => hour > currentTime
        );
        this.myForm.get('startHour')?.setValue(this.filteredEndHours[0] || '');
        this.myForm.get('endHour')?.setValue(this.filteredEndHours[0] || '');
      } else {
        this.filteredStartHours = [...this.startHours];
        this.filteredEndHours = [...this.endHours];

        this.myForm.patchValue(
          {
            startHour: '08:00',
            endHour: '17:00',
          },
          { emitEvent: false }
        );
      }

      this.filterChanged.emit(this.getFilterValues());

    });

    this.myForm.get('startHour')?.valueChanges.subscribe(() => {
      this.filterChanged.emit(this.getFilterValues());
    });

    this.myForm.get('endHour')?.valueChanges.subscribe(() => {
      this.filterChanged.emit(this.getFilterValues());
    });

    this.myForm.get('selectedPlugType')?.valueChanges.subscribe((value) => {
      this.selectedPlugType = value;
      this.filterChanged.emit(value);
      this.filterChanged.emit(this.getFilterValues());
    });

    this.plugTypes = Object.values(PlugType).filter(
      (value) => typeof value === 'string' && value !== 'NoType'
    );
  }

  getFilterValues(): FormValues {
    return {
      mapId: this.myForm.get('level')?.value,
      date: this.myForm.get('date')?.value,
      plugtype: this.myForm.get('selectedPlugType')?.value,
      startTime: this.myForm.get('startHour')?.value,
      endTime: this.myForm.get('endHour')?.value,
    };
  }

  ngOnInit() {

    this.myForm.get('selectedCar')?.valueChanges.subscribe(() => {
      this.updatePlugTypes();
    });
    this.loadCarsUser();
    this.getSelectedPlugType();

    const currentTime = this.roundToNearestHalfHour(
      new Date(this.actualStartDate.getTime() - 30 * 60 * 1000)
    );

    if (this.getDate() === this.getDate(0)) {
      this.filteredStartHours = this.startHours.filter(
        (hour) => hour >= currentTime
      );
      this.filteredEndHours = this.endHours.filter(
        (hour) => hour > currentTime
      );
    } else {
      this.filteredStartHours = this.startHours;
      this.filteredEndHours = this.endHours;
    }

    this.myForm.get('startHour')?.valueChanges.subscribe((start: string) => {
      if (start) {
        this.filteredEndHours = this.endHours.filter((end) => end > start);

        const nextHour = this.filteredEndHours[0];
        this.myForm.get('endHour')?.setValue(nextHour || '');

        this.validateHourRange();

      }
    });

    this.myForm.get('endHour')?.valueChanges.subscribe(() => {
      this.validateHourRange();
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['maps'] && this.maps.length > 0) {
      this.myForm.get('center')?.setValue(this.maps[0].centerName);
      this.updateParkingLevels(this.maps[0].centerName);
    }
  }

  get displaySelectedPlugType(): string {
    return this.selectedPlugType === 'Undefined'
      ? 'Todos los cargadores'
      : this.selectedPlugType;
  }

  updateParkingLevels(centerName: string) {
    const center = this.maps.find((c) => c.centerName === centerName);
    this.parkingLevels = center ? center.parkingLevels : [];

    const defaultParkingId =
      this.parkingLevels.length > 0 ? this.parkingLevels[0].id : 0;
    this.myForm.get('level')?.setValue(defaultParkingId);
  }

  public loadCarsUser(): void {
    this.carService.getCarsByUser().subscribe({
      next: (data: CarDto[]) => {
        console.log('Coches del usuario:', data);
        this.cars = data;
        if (this.cars.length > 0) {
          this.myForm.get('selectedCar')?.setValue(this.cars[0]);
        }
        this.updatePlugTypes();
      },
      error: (error) => {
        console.error('Error al obtener coches del usuario:', error);
      },
      complete: () => {
        console.log('Carga de coches del usuario completa');
      },
    });
  }

  updatePlugTypes(): void {
    const selectedCar = this.myForm.get('selectedCar')?.value;

    if (
      PlugType[selectedCar.plugType as keyof typeof PlugType] ==
        PlugType.Undefined &&
      selectedCar
    ) {
      this.plugTypes = Object.values(PlugType).filter(
        (value) => typeof value === 'string' && value !== 'NoType'
      );
    } else {
      this.plugTypes = [selectedCar.plugType];
    }

    this.myForm.get('selectedPlugType')?.setValue(this.plugTypes[0]);
  }

  getSelectedPlugType(): string {
    return this.selectedPlugType;
  }

  getDate(days?: number): string {
    const date = new Date();
    if (days) {
      date.setDate(date.getDate() + days);
    }
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');

    return `${year}-${month}-${day}`;
  }

  getCarId(): number {
    const car = this.myForm.value['selectedCar'];

    return car ? car.id : 0;
  }

  getCarPlate(): string {
    const car = this.myForm.value['selectedCar'];

    return car ? car.carPlate : '';
  }

  getEndTime(): string {
    const endTime: string = this.myForm.value['endHour'];

    return endTime;
  }

  getStartTime(): string {
    const startTime: string = this.myForm.value['startHour'];

    return startTime;
  }

  getCenter(): string {
    const center: string = this.myForm.value['center'];

    return center;
  }

  getLevelId(): number {
    const levelId: number = this.myForm.value['level'];

    return levelId;
  }
  getRealDate(): string {
    const date: string = this.myForm.value['date'];

    return date;
  }

  setData(): void {
    const data: SpaceData = {
      workCenter: this.getCenter(),
      date: this.getRealDate(),
      level: this.levelString ? this.levelString : '1',
      levelId: this.getLevelId(),
      car: this.getCarPlate(),
      carId: this.getCarId(),
      startTime: this.getStartTime(),
      endTime: this.getEndTime(),
    };
    console.log(data);

    this.dataRequestService.setData(data);
  }

  roundToNearestHalfHour(date: Date): string {
    const minutes = date.getMinutes();
    let hour = date.getHours();
    let roundedMinutes;

    if (minutes >= 30) {
      hour += 1;
      roundedMinutes = '00';
    } else {
      roundedMinutes = '30';
    }

    const h = hour < 10 ? '0' + hour : hour;
    return `${h}:${roundedMinutes}`;
  }

  validateHourRange() {
    const start = this.myForm.get('startHour')?.value;
    const end = this.myForm.get('endHour')?.value;

    if (!start || !end) {
      this.myForm.get('endHour')?.setErrors(null);
      return;
    }

    if (start >= end) {
      this.myForm.get('endHour')?.setErrors({ invalidRange: true });
      this.appComponent.showToast(
        'error',
        'Error',
        'La hora de fin debe ser mayor que la hora de inicio',
        3000,
        100
      );
    } else {
      this.myForm.get('endHour')?.setErrors(null);
    }
  }
}
