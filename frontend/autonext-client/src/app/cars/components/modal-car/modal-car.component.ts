import {
  Component,
  EventEmitter,
  inject,
  Input,
  Output,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { CustomModalComponent } from '@shared/components/custom-modal/custom-modal.component';
import { InputComponent } from '../../../shared/components/ui/input/input.component';
import { SelectPlugTypeComponent } from '../../../maps/components/select-plug-type/select-plug-type.component';
import { CarDto } from '../../interfaces/car.interface';
import { FormsModule } from '@angular/forms';
import { AppComponent } from '../../../app.component';

@Component({
  selector: 'cars-modal-car',
  standalone: true,
  imports: [
    CustomModalComponent,
    CommonModule,
    InputComponent,
    SelectPlugTypeComponent,
    FormsModule,
  ],
  templateUrl: './modal-car.component.html',
})
export class ModalCarComponent {
  @Input() title: string = '';
  @Input() visible: boolean = false;
  @Input() carToEdit?: CarDto;
  @Output() confirm = new EventEmitter<CarDto>();
  @Output() cancel = new EventEmitter<void>();

  @ViewChild('nameInput') nombreInput!: InputComponent;
  @ViewChild('carPlateInput') matriculaInput!: InputComponent;
  @ViewChild('plugSelect') plugSelect!: SelectPlugTypeComponent;

  private appComponent = inject(AppComponent);

  public carName: string = '';
  public carPlate: string = '';
  public plugType: string = 'Undefined';

  onConfirm() {
    this.carName = this.nombreInput.value.trim();
    this.carPlate = this.matriculaInput.value.trim();

    if (
      !this.carName ||
      !this.carPlate ||
      /\s{2,}/.test(this.carName) ||
      /\s{2,}/.test(this.carPlate)
    ) {
      this.appComponent.showToast(
        'warn',
        'Los campos no pueden estar vacíos ni contener espacios consecutivos.',
        '',
        3000
      );
      return;
    }

    this.plugType = this.plugSelect.selectedPlugType ?? 'Undefined';

    const newCar: CarDto = {
      id: this.carToEdit?.id,
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
      this.plugType = 'Undefined';
    }
  }
}
