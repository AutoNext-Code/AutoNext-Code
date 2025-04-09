import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PlugType } from '@maps/enums/plugType.enum';


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
    this.selectedPlugTypeValue = PlugType[this.selectedPlugType as keyof typeof PlugType] ?? PlugType.Undefined;
  }

}
