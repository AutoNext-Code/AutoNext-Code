import { Component, EventEmitter, inject, Output, OnInit, OnChanges, Input, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { CarService } from '@user/services/car.service';
import { CarDto } from '@user/interfaces/car.interface';
import { PlugType } from '@maps/enums/PlugType.enum';
import { CentersMaps, ParkingLevel } from '@maps/interfaces/CentersMaps.interface';
import { CenterLevel } from '@user/pages/interfaces/CenterLevel.interface';

@Component({
  selector: 'user-booking-form',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.css']
})
export class BookingFormComponent implements OnInit, OnChanges {

  private carService: CarService = inject(CarService);

  public plugTypes: (string | PlugType)[];
  public selectedPlugType: string;
  public selectedPlugTypeValue: number | null = null;
  public cars: CarDto[] = [];

  @Input() maps:CentersMaps[] = [];
    selectedCenter!:string;
    selectedLevel!:number;
    parkingLevels: ParkingLevel[] = [];
    CenterLevel?: CenterLevel;
    myForm!: FormGroup;

  @Output() mapSelected: EventEmitter<number> = new EventEmitter<number>();

  constructor() {
    this.myForm = new FormGroup({
      center: new FormControl(''),
      level: new FormControl(''),
      selectedCar: new FormControl(null),
      selectedPlugType: new FormControl(null),
      date: new FormControl(this.getDate()),
      startHour: new FormControl('8:00'),
      endHour: new FormControl('17:00'),
    });

    this.myForm.get('center')?.valueChanges.subscribe(value => {
      this.updateParkingLevels(value);
    });

    this.myForm.get('level')?.valueChanges.subscribe(value => {
      console.log(value);
      this.mapSelected.emit(value);
    });
    this.plugTypes = Object.values(PlugType)
      .filter(value => typeof value === 'string' && value !== 'NoType');

    this.selectedPlugType = "Undefined";
    
  }

  ngOnInit() {
    this.myForm.get('selectedCar')?.valueChanges.subscribe(() => {
      this.updatePlugTypes();
    });
    this.loadCarsUser();
    this.getSelectedPlugTypeValue();
  }
  
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['maps'] && this.maps.length > 0) {
      this.myForm.get('center')?.setValue(this.maps[0].centerName);
      this.updateParkingLevels(this.maps[0].centerName);
    }
  }

  get displaySelectedPlugType(): string {
    return this.selectedPlugType === "Undefined" ? "Todos los cargadores" : this.selectedPlugType;
  }

  updateParkingLevels(centerName: string) {
    const center = this.maps.find(c => c.centerName === centerName);
    this.parkingLevels = center ? center.parkingLevels : [];

    const defaultParkingId = this.parkingLevels.length > 0 ? this.parkingLevels[0].id : 0;
    this.myForm.get('level')?.setValue(defaultParkingId);

    console.log(defaultParkingId)
    this.mapSelected.emit(defaultParkingId);
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
      }
    })
  }

  updatePlugTypes(): void {

    const selectedCar = this.myForm.get('selectedCar')?.value;

    if (PlugType[selectedCar.plugType as keyof typeof PlugType] == PlugType.Undefined && selectedCar) {
      this.plugTypes = Object.values(PlugType)
        .filter(value => typeof value === 'string' && value !== 'NoType');
    } else {
      this.plugTypes = [selectedCar.plugType];
    }

    this.myForm.get('selectedPlugType')?.setValue(this.plugTypes[0]);

  }

  getSelectedPlugTypeValue(): void {
    this.selectedPlugTypeValue = PlugType[this.selectedPlugType as keyof typeof PlugType] ?? PlugType.Undefined;
  }

  startHours: string[] = [
    '8:00', '8:30', '9:00', '9:30', '10:00', '10:30', '11:00', '11:30',
    '12:00', '12:30', '13:00', '13:30', '14:00', '14:30', '15:00', '15:30',
    '16:00', '16:30', '17:00', '17:30', '18:00', '18:30', '19:00', '19:30'
  ];

  endHours: string[] = [
    '8:30', '9:00', '9:30', '10:00', '10:30', '11:00', '11:30', '12:00',
    '12:30', '13:00', '13:30', '14:00', '14:30', '15:00', '15:30', '16:00',
    '16:30', '17:00', '17:30', '18:00', '18:30', '19:00', '19:30', '20:00'
  ];

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


}
