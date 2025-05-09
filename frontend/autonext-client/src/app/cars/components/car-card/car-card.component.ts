import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CarDto } from '../../interfaces/car.interface';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'car-card',
  imports: [CommonModule],
  templateUrl: './car-card.component.html',
  styleUrl: './car-card.component.css'
})
export class CarCardComponent {
  @Input() car: CarDto | null = null;
  @Output() delete = new EventEmitter<number>();
  @Output() edit = new EventEmitter<CarDto>();

  public showModalEdit: boolean = false;

  openModalEdit() {
    this.edit.emit(this.car!);
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

  getPlugLabel(value: string | undefined): string {
    if (!value) return 'Sin especificar';
    return value === 'Undefined' ? 'Sin especificar' : value;
  }

}
