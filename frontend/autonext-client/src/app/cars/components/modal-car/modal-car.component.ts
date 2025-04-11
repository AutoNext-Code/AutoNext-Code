import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild, ViewEncapsulation } from '@angular/core';

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
export class ModalCarComponent implements OnChanges {

  @Input() title: string = '';
  @Input() visible: boolean = false;
  @Input() carToEdit?: CarDto;
  @Output() confirm = new EventEmitter<any>();
  @Output() cancel = new EventEmitter<void>();

  @ViewChild('nameInput') nombreInput!: InputComponent;
  @ViewChild('carPlateInput') matriculaInput!: InputComponent;
  @ViewChild('plugSelect') plugSelect!: SelectPlugTypeComponent;

  public carName: string = '';
  public carPlate: string = '';
  public plugType: PlugType = PlugType.Undefined;

  onConfirm() {
    this.carName = this.nombreInput.value;
    this.carPlate = this.matriculaInput.value;
    this.plugType = this.plugSelect.selectedPlugTypeValue ?? PlugType.Undefined;

    const newCar: CarDto = {
      name: this.carName,
      carPlate: this.carPlate,
      plugType: this.plugType,
    };

    console.log('CarDto:', newCar);
    this.confirm.emit(newCar);
  }

  onCancel() {
    this.cancel.emit();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['carToEdit'] && this.carToEdit) {
      this.carName = this.carToEdit.name;
      this.carPlate = this.carToEdit.carPlate;
      this.plugType = this.carToEdit.plugType;
    } else {
      this.carName = '';
      this.carPlate = '';
      this.plugType = PlugType.Undefined;
    }
  }
}
