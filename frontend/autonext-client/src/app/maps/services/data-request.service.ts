import {  Injectable } from '@angular/core';
import { SpaceData } from '@booking/interfaces/spaceData.interface';

@Injectable({
  providedIn: 'root',
})
export class DataRequestService {

  private data!: SpaceData;

  getData(): SpaceData {
    return this.data ;
  }
  
  setData(data: SpaceData): void {
    this.data = data ;
  }

}
