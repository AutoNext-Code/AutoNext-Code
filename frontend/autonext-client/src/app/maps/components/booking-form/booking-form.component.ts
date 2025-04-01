import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SelectPlugTypeComponent } from "../select-plug-type/select-plug-type.component";

@Component({
  selector: 'user-booking-form',
  imports: [ReactiveFormsModule, SelectPlugTypeComponent],
  templateUrl: './booking-form.component.html',
  styleUrl: './booking-form.component.css'
})
export class BookingFormComponent {


   ciudades = ["Madrid", "Málaga"];
    plantasCiudad = {
      "Madrid": ['1','2','3'],
      "Málaga": ['4','5']
    };

    @Output() mapSelected: EventEmitter<string> = new EventEmitter();


    myForm!: FormGroup;

    ngOnInit() {
      this.myForm = new FormGroup({
        selectedMap: new FormControl('mapa1')
      });
      this.mapSelected.emit("1");
    }

    updateMap(map: { catSelected: string, subCatSelected:string}) {
      this.mapSelected.emit(`${map.subCatSelected}`);
    }

}
