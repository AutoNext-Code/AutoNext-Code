import { Component, Input, OnInit } from '@angular/core';
import { SpaceData } from '@booking/interfaces/spaceData.interface';

@Component({
  selector: 'space-data',
  imports: [],
  templateUrl: './space-data.component.html',
  styleUrl: './space-data.component.css'
})
export class SpaceDataComponent {

  @Input({required: true})
  spaceData!: SpaceData ;

}
