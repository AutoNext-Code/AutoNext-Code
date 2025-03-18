import { Component, Input } from '@angular/core';
import { AdminCard } from './interfaces/card.interface';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'admin-card',
  imports: [RouterModule],
  templateUrl: './admin-card.component.html',
  styleUrl: './admin-card.component.css'
})
export class AdminCardComponent {

  @Input({required: true})
   cardData!: AdminCard ;

}
