import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PlugType } from '@maps/enums/PlugType.enum';

@Component({
  selector: 'maps-select-plug-type',
  imports: [FormsModule, CommonModule],
  templateUrl: './select-plug-type.component.html',
  styleUrl: './select-plug-type.component.css'
})
export class SelectPlugTypeComponent implements OnInit {
  public plugTypes: (string | PlugType)[];
  public selectedPlugType: string;

  public selectedPlugTypeValue: number | null = null;


  constructor() {
    this.plugTypes = Object.values(PlugType)
      .filter(value => typeof value === 'string' && value !== 'NoType');

    this.selectedPlugType = "Undefined";
  }

  ngOnInit(): void {
      this.getSelectedPlugTypeValue();
  }

  get displaySelectedPlugType(): string {
    return this.selectedPlugType === "Undefined" ? "Todos los cargadores" : this.selectedPlugType;
  }

  getSelectedPlugTypeValue(): void {
    switch (this.selectedPlugType) {
      case "Schuko":
        this.selectedPlugTypeValue = PlugType.Schuko;
        break;
      case "Type1":
        this.selectedPlugTypeValue = PlugType.Type1;
        break;
      case "Type2":
        this.selectedPlugTypeValue = PlugType.Type2;
        break;
      case "CCS":
        this.selectedPlugTypeValue = PlugType.CCS;
        break;
      case "CHAdeMO":
        this.selectedPlugTypeValue = PlugType.CHAdeMO;
        break;
      case "Undefined":
      default:
        this.selectedPlugTypeValue = PlugType.Undefined;
        break;
    }

    console.log("Selected PlugType:", this.selectedPlugType);
    console.log("Numeric Value:", this.selectedPlugTypeValue);
  }



}
