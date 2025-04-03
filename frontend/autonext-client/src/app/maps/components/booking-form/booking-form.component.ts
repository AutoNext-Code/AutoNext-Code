import { Component, EventEmitter, inject, Output, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { CarService } from '@user/services/car.service';
import { CarDto } from '@user/interfaces/car.interface';
import { PlugType } from '@maps/enums/PlugType.enum';

@Component({
  selector: 'user-booking-form',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './booking-form.component.html',
  styleUrls: ['./booking-form.component.css']
})
export class BookingFormComponent implements OnInit {

  private carService: CarService = inject(CarService);

  public plugTypes: (string | PlugType)[];
  public selectedPlugType: string;
  public selectedPlugTypeValue: number | null = null;
  public cars: CarDto[] = [];

  @Output() mapSelected: EventEmitter<string> = new EventEmitter();

  myForm!: FormGroup;

  constructor() {
    this.plugTypes = Object.values(PlugType)
      .filter(value => typeof value === 'string' && value !== 'NoType');

    this.selectedPlugType = "Undefined";
  }

  ngOnInit() {
    this.myForm = new FormGroup({
      selectedMap: new FormControl('mapa1'),
      selectedCar: new FormControl(null),
      selectedPlugType: new FormControl(null)
    });
    this.myForm.get('selectedCar')?.valueChanges.subscribe(() => {
      this.updatePlugTypes();
    });
    this.mapSelected.emit("1");
    this.loadCarsUser();
    this.getSelectedPlugTypeValue();
  }

  get displaySelectedPlugType(): string {
    return this.selectedPlugType === "Undefined" ? "Todos los cargadores" : this.selectedPlugType;
  }

  updateMap(map: { catSelected: string, subCatSelected: string }) {
    this.mapSelected.emit(`${map.subCatSelected}`);
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
}