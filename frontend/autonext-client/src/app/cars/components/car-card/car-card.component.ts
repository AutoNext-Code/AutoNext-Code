import { Component, Input } from '@angular/core';
import { CarDto } from '../../interfaces/car.interface';
import { CommonModule } from '@angular/common';
import { ModalCarComponent } from "../modal-car/modal-car.component";

@Component({
  selector: 'car-card',
  imports: [CommonModule, ModalCarComponent],
  templateUrl: './car-card.component.html',
  styleUrl: './car-card.component.css'
})
export class CarCardComponent {
  @Input() car: CarDto | null = null;

  public showModalEdit: boolean = false;

  openModalEdit() {
    this.showModalEdit = true;
  }

  closeModalEdit() {
    this.showModalEdit = false;
  }
}
