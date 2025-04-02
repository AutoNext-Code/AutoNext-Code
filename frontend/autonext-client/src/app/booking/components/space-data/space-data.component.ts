import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { SpaceData } from '@booking/interfaces/spaceData.interface';
import { CustomButtonComponent } from "../../../shared/components/ui/custom-button/custom-button.component";

@Component({
  selector: 'space-data',
  imports: [CustomButtonComponent],
  templateUrl: './space-data.component.html',
  styleUrl: './space-data.component.css'
})
export class SpaceDataComponent {

  @Input({required: true})
  spaceData!: SpaceData ;

  @Output()
  modalEmitter = new EventEmitter<void>() ;

  
  closeModal() {
    this.modalEmitter.emit();
  }


}
