import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PlugType } from '@maps/enums/plugType.enum';

@Component({
  selector: 'maps-select-plug-type',
  imports: [FormsModule, CommonModule],
  templateUrl: './select-plug-type.component.html',
  styleUrl: './select-plug-type.component.css'
})
export class SelectPlugTypeComponent {
  public plugTypes: (string | PlugType)[];
  public selectedPlugType: string;

  constructor() {
    this.plugTypes = Object.values(PlugType)
      .filter(value => typeof value === 'string' && value !== 'NoType');

    this.selectedPlugType = "Undefined";
  }
}
