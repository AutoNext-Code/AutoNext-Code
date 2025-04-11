import { Component, Input } from '@angular/core';
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
}
