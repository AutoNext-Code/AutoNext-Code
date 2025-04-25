import {
  Component,
  EventEmitter,
  inject,
  Output,
  OnInit,
  Input,
  ViewChild,
  ElementRef,
  signal,
  ChangeDetectorRef,
} from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { CarDto } from '../../../cars/interfaces/car.interface';
import { CarService } from '../../../cars/services/car.service';

import { PlugType } from '@maps/enums/plugType.enum';
import { CentersMaps, ParkingLevel, } from '@maps/interfaces/CentersMaps.interface';

import { FormValues } from '@maps/interfaces/FormValues.interface';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { BookingFormUtils } from '../../../../utils/bookingForm-utils';
import { DateUtilsService } from '@maps/services/date-utils.service';
import { BookingFormService } from '@maps/services/booking-form.service';

@Component({
  selector: 'user-booking-form',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.css'],
})
export class BookingFormComponent implements OnInit {

  private fb = inject(FormBuilder);
  private cdr = inject(ChangeDetectorRef);
  private carService = inject(CarService);
  dateUtilsService = inject(DateUtilsService);
  private bookingFormService = inject(BookingFormService);
  bookingFormUtils = BookingFormUtils;

  @ViewChild('levelSelect') level!: ElementRef<HTMLSelectElement>;

  @Input() set maps(value: CentersMaps[]) {
    this._maps = value;
    if (value.length > 0) {
      this.setDefaultCenterAndParkingLevels(value[0].centerName);
    }
  }
  get maps(): CentersMaps[] {
    return this._maps;
  }

  @Output() filterChanged = new EventEmitter<FormValues>();

  plugTypes: (string | PlugType)[];
  cars = signal<CarDto[]>([]);
  filteredStartHours: string[] = [];
  filteredEndHours: string[] = [];

  private _maps: CentersMaps[] = [];
  parkingLevels: ParkingLevel[] = [];
  private levelString!: string;
  selectedPlugType = 'Undefined';

  constructor() {
    this.plugTypes = this.getInitialPlugTypes();
  }

  ngOnInit() {
    this.initializeFormValueChanges();
    this.loadInitialData();
  }

  myForm: FormGroup = this.fb.group({
    center: [''],
    level: [''],
    selectedCar: [null],
    selectedPlugType: ['Undefined'],
    date: [this.dateUtilsService.getDate()],
    startHour: [this.bookingFormUtils.roundToNearestHalfHour(new Date())],
    endHour: [this.bookingFormUtils.roundToNearestHalfHour(new Date(), 30 * 60 * 1000)]
  }
  );

  private getInitialPlugTypes(): (string | PlugType)[] {
    return Object.values(PlugType).filter(
      (value) => typeof value === 'string' && value !== 'NoType'
    );
  }

  private initializeFormValueChanges(): void {
    this.myForm.valueChanges
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe(() => {
        this.filterChanged.emit(this.getFilterValues());
        this.bookingFormService.setData(this.myForm, this.levelString);
      });

    this.myForm.get('center')?.valueChanges.subscribe((center) => {
      this.parkingLevels = this.bookingFormService.updateParkingLevels(center, this.maps, this.myForm);
      this.updateLevelString();
    });

    this.myForm.get('level')?.valueChanges.subscribe(() => {
      this.updateLevelString();
    });

    this.myForm.get('selectedCar')?.valueChanges.subscribe((selectedCar) => {
      this.plugTypes = this.bookingFormService.updatePlugTypes(selectedCar);
      this.myForm.get('selectedPlugType')?.setValue(this.plugTypes[0]);
    });
  }

  private loadInitialData(): void {
    const now = new Date();
    const minutes = now.getMinutes();
    const shouldApplyOffset = (minutes >= 0 && minutes < 10 ) || (minutes >= 30 && minutes < 40);

    const currentTime = shouldApplyOffset
      ? this.bookingFormUtils.roundToNearestHalfHour(now, -30 * 60 * 1000)
      : this.bookingFormUtils.roundToNearestHalfHour(now, 0);

    this.updateHoursForToday(currentTime);
    this.initializeDateValueChanges();
    this.initializeStartHourValueChanges();
    this.loadCarsUser();
  }

  private setDefaultCenterAndParkingLevels(centerName: string): void {
    this.myForm.get('center')?.setValue(centerName);
    this.parkingLevels = this.bookingFormService.updateParkingLevels(centerName, this.maps, this.myForm);
  }

  private updateLevelString(): void {
    this.cdr.detectChanges();
    const nativeSelect = this.level.nativeElement;
    const selectedOption = nativeSelect.options[nativeSelect.selectedIndex];
    this.levelString = selectedOption ? selectedOption.text : '';
  }

  private initializeDateValueChanges(): void {
    this.myForm.get('date')?.valueChanges.subscribe((selectedDate) => {
      if (!selectedDate) return;

      const isToday = this.dateUtilsService.isSelectedDateToday(selectedDate);
      if (isToday) {
        const now = new Date();
        const minutes = now.getMinutes();
        const shouldApplyOffset = (minutes >= 0 && minutes < 10 ) || (minutes >= 30 && minutes < 40);
    
        const currentTime = shouldApplyOffset
          ? this.bookingFormUtils.roundToNearestHalfHour(now, -30 * 60 * 1000)
          : this.bookingFormUtils.roundToNearestHalfHour(now, 0);
        this.updateHoursForToday(currentTime);
      } else {
        this.resetHoursForDefault();
      }
    });
  }

  private initializeStartHourValueChanges(): void {
    this.myForm.get('startHour')?.valueChanges.subscribe((start) => {
      if (start) {
        this.filteredEndHours = this.bookingFormUtils.endHours.filter(
          (end) => end > start
        );
        console.log('Filtered End Hours:', this.filteredEndHours);
        this.myForm.get('endHour')?.setValue(this.filteredEndHours[0] || '');
      }
    });
  }

  private updateHoursForToday(currentTime: string): void {
    const { filteredStartHours, filteredEndHours } = this.dateUtilsService.updateHoursForToday(
      this.myForm,
      currentTime
    );
    this.filteredStartHours = filteredStartHours;
    this.filteredEndHours = filteredEndHours;
  }

  private resetHoursForDefault(): void {
    const { filteredStartHours, filteredEndHours } = this.dateUtilsService.resetHoursForDefault(
      this.myForm
    );
    this.filteredStartHours = filteredStartHours;
    this.filteredEndHours = filteredEndHours;
  }

  private loadCarsUser(): void {
    this.carService.getCarsByUser().subscribe({
      next: (cars) => {
        this.cars.set(cars);
        if (cars.length) {
          this.myForm.get('selectedCar')?.setValue(cars[0]);
        }
      },
      error: (err) => console.error('Error loading user cars:', err),
    });
  }

  private getFilterValues(): FormValues {
    return {
      mapId: this.myForm.get('level')?.value,
      date: this.myForm.get('date')?.value,
      plugtype: this.myForm.get('selectedPlugType')?.value,
      startTime: this.myForm.get('startHour')?.value,
      endTime: this.myForm.get('endHour')?.value,
    };
  }
}