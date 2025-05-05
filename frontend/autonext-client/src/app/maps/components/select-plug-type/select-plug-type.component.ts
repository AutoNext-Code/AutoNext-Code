import { CommonModule } from '@angular/common';
import { Component, forwardRef, OnInit } from '@angular/core';
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR } from '@angular/forms';
import { PlugType } from '@maps/enums/plugType.enum';

@Component({
  selector: 'maps-select-plug-type',
  imports: [FormsModule, CommonModule],
  templateUrl: './select-plug-type.component.html',
  styleUrls: ['./select-plug-type.component.css'],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => SelectPlugTypeComponent),
    multi: true
  }]
})
export class SelectPlugTypeComponent implements OnInit, ControlValueAccessor {
  public plugTypes: (string | PlugType)[];
  public selectedPlugType: string = 'Undefined';
  public selectedPlugTypeValue: number | null = null;
  private onChange: (v: any) => void = () => {};
  private onTouched: () => void = () => {};
  public isDisabled = false;

  constructor() {
    this.plugTypes = ['Undefined', ...Object.values(PlugType)
      .filter(v => typeof v === 'string' && v !== 'NoType' && v !== 'Undefined')];
  }


  ngOnInit(): void {
    this.updateValue(this.selectedPlugType);
  }

  writeValue(value: any): void {
    let val: string;

    if (value === undefined || value === null) {
      val = 'Undefined';
    } else if (typeof value === 'number') {
      val = PlugType[value] ?? 'Undefined';
    } else {
      val = value;
    }

    this.updateValue(val);
  }


  registerOnChange(fn: any): void { this.onChange = fn; }
  registerOnTouched(fn: any): void { this.onTouched = fn; }
  setDisabledState?(isDisabled: boolean): void { this.isDisabled = isDisabled; }

  getSelectedPlugTypeValue(): void {
    this.selectedPlugTypeValue =
      PlugType[this.selectedPlugType as keyof typeof PlugType] ?? PlugType.Undefined;
  }

  onBlur(): void {
    this.onTouched();
  }

  private updateValue(value: string) {
    this.selectedPlugType = value;
    this.getSelectedPlugTypeValue();
    this.onChange(this.selectedPlugType);
  }
}
