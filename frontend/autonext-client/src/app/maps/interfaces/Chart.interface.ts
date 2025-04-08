import { Space } from "./Space.interface";

export interface Chart {
  id: number;
  name: String;
  imageUrl: String;
  workCenterName: String;
  spaces: Space[];
}


