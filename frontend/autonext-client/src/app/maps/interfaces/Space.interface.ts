import { Direction } from "@maps/enums/direction.enum";
import { PlugType } from "@maps/enums/plugType.enum";
import { State } from "@maps/enums/state.enum";




export interface Space  {
  id:number;
  x: number;
  y: number;
  direction: Direction;
  plugType: PlugType;
  state: State;
}
