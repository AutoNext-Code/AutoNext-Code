import { Component, EventEmitter, Input, Output, SimpleChanges, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomModalComponent } from '@shared/components/custom-modal/custom-modal.component';
import { InputComponent } from "../../../shared/components/ui/input/input.component";
import { SelectPlugTypeComponent } from "../../../maps/components/select-plug-type/select-plug-type.component";
import { CarDto } from '../../interfaces/car.interface';
import { PlugType } from '@maps/enums/plugType.enum';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'cars-modal-car',
  standalone: true,
  imports: [CustomModalComponent, CommonModule, InputComponent, SelectPlugTypeComponent, FormsModule],
  templateUrl: './modal-car.component.html',
})
export class ModalCarComponent {
  @Input() title: string = '';
  @Input() visible: boolean = false;
  @Input() carToEdit?: CarDto;  // Recibimos el coche a editar
  @Output() confirm = new EventEmitter<CarDto>();
  @Output() cancel = new EventEmitter<void>();

  @ViewChild('nameInput') nombreInput!: InputComponent;
  @ViewChild('carPlateInput') matriculaInput!: InputComponent;
  @ViewChild('plugSelect') plugSelect!: SelectPlugTypeComponent;

  public carName: string = '';
  public carPlate: string = '';
  public plugType: string = 'Undefined';  // <-- Usamos string para el plugType

  // Método para manejar la confirmación (creación/edición)
  onConfirm() {
    this.carName = this.nombreInput.value;
    this.carPlate = this.matriculaInput.value;
    this.plugType = this.plugSelect.selectedPlugType ?? 'Undefined';  // Enviamos el valor como string

    const newCar: CarDto = {
      id: this.carToEdit?.id,
      name: this.carName,
      carPlate: this.carPlate,
      plugType: this.plugType // Asegúrate de que esto sea un string
    };

    console.log('CarDto:', newCar);
    this.confirm.emit(newCar); // Emitimos el coche al componente padre
  }

  // Método para cancelar la edición/creación
  onCancel() {
    this.cancel.emit();  // Emitimos el evento de cancelación
  }

  // Detectamos cambios en los inputs y actualizamos el estado
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['carToEdit'] && this.carToEdit) {
      // Si hay coche para editar, actualizamos los valores
      this.carName = this.carToEdit.name;
      this.carPlate = this.carToEdit.carPlate;
      this.plugType = this.carToEdit.plugType;  // Esto ahora debe ser un string
    } else {
      // Si no hay coche a editar (creación), limpiamos los campos
      this.carName = '';
      this.carPlate = '';
      this.plugType = 'Undefined';  // Restablecemos a string
    }
  }

}
