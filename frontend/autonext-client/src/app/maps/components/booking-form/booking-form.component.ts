import { Component, EventEmitter, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SelectPlugTypeComponent } from "../select-plug-type/select-plug-type.component";

@Component({
  selector: 'user-booking-form',
  imports: [ReactiveFormsModule, SelectPlugTypeComponent],
  templateUrl: './booking-form.component.html',
  styleUrl: './booking-form.component.css'
})
export class BookingFormComponent implements OnChanges{


    centers = ["Madrid", "Málaga"];
    maps = {
      "Madrid": [1,2,3],
      "Málaga": [0,1]
    };
    visibleMaps: number[] = this.maps["Madrid"];

    @Output() mapSelected: EventEmitter<number> = new EventEmitter();


    myForm!: FormGroup;

    ngOnInit() {
      this.myForm = new FormGroup({
        selectedMap: new FormControl(this.maps["Madrid"][0]),
        selectedCenter: new FormControl("Madrid")
      });
      this.mapSelected.emit(0);
    }

    ngOnChanges(changes: SimpleChanges): void {
      if(changes['centers'] && this.centers.length > 0 && !this.myForm.get('center')?.value){
        this.myForm.get('center')?.setValue(this.centers[0]);
        this.updateMaps();
      }
    }

    updateMap(map: { catSelected: number, subCatSelected:number}) {
      this.mapSelected.emit(map.subCatSelected);
    }

    updateMaps(){

      const actualCenter = this.myForm.get('selectedCenter')?.value as keyof typeof this.maps;


      if(actualCenter in this.maps){
        this.visibleMaps = this.maps[actualCenter];
        this.myForm.get("selectedMap")?.setValue(this.visibleMaps[0]);
      }else{
        console.error(`"${actualCenter}" no es una clave válida para maps`);
      }

    }

}
