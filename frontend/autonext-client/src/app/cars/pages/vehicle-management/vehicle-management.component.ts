import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { HeaderComponent } from '@shared/header/header.component';
import { CarCardComponent } from '../../components/car-card/car-card.component';
import { CarService } from '../../services/car.service';
import { Subscription } from 'rxjs';
import { CarDto } from '../../interfaces/car.interface';
import { CommonModule } from '@angular/common';
import { ModalCarComponent } from "../../components/modal-car/modal-car.component";
import { AppComponent } from '../../../app.component';
import { CustomModalComponent } from "../../../shared/components/custom-modal/custom-modal.component";

@Component({
  imports: [HeaderComponent, CarCardComponent, CommonModule, ModalCarComponent, CustomModalComponent],
  templateUrl: './vehicle-management.component.html',
  styleUrl: './vehicle-management.component.css'
})
export class VehicleManagementComponent implements OnInit, OnDestroy  {
  private carService: CarService = inject(CarService);
  private appComponent: AppComponent = inject(AppComponent);

  public cars: CarDto[];
  private subscription: Subscription;
  public showModalEdit: boolean;
  public showConfirmModal: boolean = false;
  public carToDeleteId: number | null = null;
  public carToEdit: CarDto | null = null;

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
        this.appComponent.showToast('error', 'Error al obtener coches del usuario. ',"", 3000);
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  openModal() {
    this.showModalEdit = true;
    this.carToEdit = null;
  }

  closeModal() {
    this.showModalEdit = false;
  }

  openEditModal(car: CarDto) {
    this.showModalEdit = true;
    this.carToEdit = car;
  }

  handleNewCar(newCar: CarDto) {
    this.carService.createCar(newCar).subscribe({
      next: (response: string) => {
        this.closeModal();
        this.loadCars();
        this.appComponent.showToast('success', '', response, 3000);
      },
      error: () => {
        this.appComponent.showToast('error', 'Error al añadir el coche.', "", 3000);
      }
    });
  }

  handleEditCar(updatedCar: CarDto) {
    console.log(updatedCar)
    if (updatedCar.id) {
      this.carService.updateCar(updatedCar).subscribe({
        next: (response) => {
          this.closeModal();
          this.loadCars();
          this.appComponent.showToast('success', 'Coche actualizado', '', 3000);
        },
        error: () => {
          this.appComponent.showToast('error', 'Error al actualizar el coche.', "", 3000);
        }
      });
    } else {
      this.appComponent.showToast('error', 'El coche no tiene ID para actualizar.', "", 3000);
    }
  }

  openDeleteConfirmation(id: number) {
    this.carToDeleteId = id;
    this.showConfirmModal = true;
  }

  confirmDelete() {
    if (this.carToDeleteId !== null) {
      this.carService.deleteCar(this.carToDeleteId).subscribe({
        next: (response: string) => {
          this.loadCars();
          this.appComponent.showToast('success', '', response, 3000);
          this.resetDeleteState();
        },
        error: () => {
          this.appComponent.showToast('error', 'Error al eliminar el coche.', '', 3000);
          this.resetDeleteState();
        }
      });
    }
  }

  cancelDelete() {
    this.resetDeleteState();
  }

  private resetDeleteState() {
    this.showConfirmModal = false;
    this.carToDeleteId = null;
  }
}
