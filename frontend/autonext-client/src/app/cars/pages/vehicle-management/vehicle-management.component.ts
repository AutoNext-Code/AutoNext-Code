import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { HeaderComponent } from '@shared/header/header.component';
import { CarCardComponent } from '../../components/car-card/car-card.component';
import { CarService } from '../../services/car.service';
import { Subscription } from 'rxjs';
import { CarDto } from '../../interfaces/car.interface';
import { CommonModule } from '@angular/common';
import { ModalCarComponent } from "../../components/modal-car/modal-car.component";

@Component({
  imports: [HeaderComponent, CarCardComponent, CommonModule, ModalCarComponent],
  templateUrl: './vehicle-management.component.html',
  styleUrl: './vehicle-management.component.css'
})
export class VehicleManagementComponent implements OnInit, OnDestroy  {


  private carService: CarService = inject(CarService);

  public cars: CarDto[];
  private subscription: Subscription;
  public showModalEdit: boolean;

  constructor() {
    this.cars = [];
    this.showModalEdit = false;
    this.subscription = new Subscription();
  }

  ngOnInit(): void {
    this.subscription = this.carService.getCarsByUser().subscribe({
      next: (cars: CarDto[]) => {
        this.cars = cars;
        console.log(this.cars);
      },
      error: (error) => {
        console.error('Error al obtener coches del usuario:', error);
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  openModal() {
    this.showModalEdit = true;
  }

  closeModal() {
    this.showModalEdit = false;
  }

  handleNewCar(newCar: CarDto) {
    // this.carService.addCar(newCar).subscribe({
    //   next: (car: CarDto) => {
    //     this.cars.push(car); // O actualiza la lista si necesitas recargar
    //     this.closeModal();
    //   },
    //   error: (error) => {
    //     console.error('Error al añadir coche:', error);
    //   }
    // });

    console.log(newCar);
    this.closeModal();

  }


}
