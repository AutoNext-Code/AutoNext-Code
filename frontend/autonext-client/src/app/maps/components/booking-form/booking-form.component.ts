import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SelectPlugTypeComponent } from "../select-plug-type/select-plug-type.component";
import { CentersMaps, ParkingLevel } from '@maps/interfaces/CentersMaps.interface';
import { CenterLevel } from '@user/pages/interfaces/CenterLevel.interface';

@Component({
  selector: 'user-booking-form',
  imports: [ReactiveFormsModule, SelectPlugTypeComponent],
  templateUrl: './booking-form.component.html',
  styleUrl: './booking-form.component.css'
})
export class BookingFormComponent implements  OnChanges{


    @Input() maps:CentersMaps[] = [];
    selectedCenter!:string;
    selectedLevel!:number;
    parkingLevels: ParkingLevel[] = [];
    CenterLevel?: CenterLevel;
    myForm!: FormGroup;

    @Output() mapSelected: EventEmitter<number> = new EventEmitter<number>();

    constructor(){

      this.myForm = new FormGroup({
        center: new FormControl(''),
        level: new FormControl(''),
      });

      this.myForm.get('center')?.valueChanges.subscribe(value => {
        this.updateParkingLevels(value);
      });

      this.myForm.get('level')?.valueChanges.subscribe(value => {
        console.log(value);
        this.mapSelected.emit(value);
      });

    }
    ngOnChanges(changes: SimpleChanges): void {
      if (changes['maps'] && this.maps.length > 0) {
        this.myForm.get('center')?.setValue(this.maps[0].centerName);
        this.updateParkingLevels(this.maps[0].centerName);
      }
    }


    updateParkingLevels(centerName: string) {
      const center = this.maps.find(c => c.centerName === centerName);
      this.parkingLevels = center ? center.parkingLevels : [];

      const defaultParkingId = this.parkingLevels.length > 0 ? this.parkingLevels[0].id : 0;
      this.myForm.get('level')?.setValue(defaultParkingId);

      console.log(defaultParkingId)
      this.mapSelected.emit(defaultParkingId);
    }




}
