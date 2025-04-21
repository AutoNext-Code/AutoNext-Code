import { Component, EventEmitter, Input, Output } from '@angular/core';
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
  @Output() delete = new EventEmitter<number>();
  @Output() edit = new EventEmitter<CarDto>();

  public showModalEdit: boolean = false;

  openModalEdit() {
    // this.showModalEdit = true;
    this.edit.emit(this.car!); // Emitimos el coche para edición
    console.log(this.car);
  }

  closeModalEdit() {
    this.showModalEdit = false;
  }

  onDelete() {
    if (this.car?.id) {
      this.delete.emit(this.car.id);
    }
  }
}
