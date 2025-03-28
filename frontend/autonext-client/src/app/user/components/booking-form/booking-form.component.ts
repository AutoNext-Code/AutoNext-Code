import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { SelectDepComponent } from '@shared/components/ui/select-dep/select-dep.component';

@Component({
  selector: 'user-booking-form',
  imports: [ReactiveFormsModule, SelectDepComponent],
  templateUrl: './booking-form.component.html',
  styleUrl: './booking-form.component.css'
})
export class BookingFormComponent {

   ciudades = ["Madrid", "Málaga"];
    plantasCiudad = {
      "Madrid": ['1','2','3'],
      "Málaga": ['0','1']
    };
  
    @Output() mapSelected: EventEmitter<string> = new EventEmitter();
  
  
    myForm!: FormGroup;
  
    ngOnInit() {
      this.myForm = new FormGroup({
        selectedMap: new FormControl('mapa1')
      });
    }

    updateMap(map: { catSelected: string, subCatSelected:string}) {
      this.mapSelected.emit(`${map.catSelected}-${map.subCatSelected}`);

    }

}
