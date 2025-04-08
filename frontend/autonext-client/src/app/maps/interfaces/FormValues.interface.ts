import { PlugType } from "@maps/enums/plugType.enum";

export interface FormValues{
  mapId:number,
  date:string,
  plugtype:PlugType,
  startTime:string,
  endTime:string
}
