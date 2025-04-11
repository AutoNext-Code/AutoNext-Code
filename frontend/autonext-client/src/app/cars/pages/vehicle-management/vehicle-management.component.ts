import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { HeaderComponent } from '@shared/header/header.component';
import { CarCardComponent } from '../../components/car-card/car-card.component';
import { CarService } from '../../services/car.service';
import { Subscription } from 'rxjs';
import { CarDto } from '../../interfaces/car.interface';
import { CommonModule } from '@angular/common';
import { ModalCarComponent } from "../../components/modal-car/modal-car.component";
import { AppComponent } from '../../../app.component';

@Component({
  imports: [HeaderComponent, CarCardComponent, CommonModule, ModalCarComponent],
  templateUrl: './vehicle-management.component.html',
  styleUrl: './vehicle-management.component.css'
})
export class VehicleManagementComponent implements OnInit, OnDestroy  {


  private carService: CarService = inject(CarService);
  private appComponent: AppComponent = inject(AppComponent);

  public cars: CarDto[];
  private subscription: Subscription;
  public showModalEdit: boolean;

  constructor() {
    this.cars = [];
    this.showModalEdit = false;
    this.subscription = new Subscription();
  }

  ngOnInit(): void {
    this.loadCars();
  }

  private loadCars(): void {
    this.subscription = this.carService.getCarsByUser().subscribe({
      next: (cars: CarDto[]) => {
        this.cars = cars;
      },
      error: (error) => {
        this.appComponent.showToast('error', '❌ Error al obtener coches del usuario. ',"", 3000);
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
    this.carService.createCar(newCar).subscribe({
      next: (response: string) => {
        this.closeModal();
        this.loadCars();
        this.appComponent.showToast('success', '', response, 3000);
      },
      error: (error) => {
        this.appComponent.showToast('error', '❌ Error al añadir el coche.', "", 3000);
      }
    });
  }



}
