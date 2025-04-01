import { Direction } from "@maps/enums/Direction.enum";
import { PlugType } from "@maps/enums/plugType.enum";
import { State } from "@maps/enums/State.enum";


export interface Space  {
  id:number;
  x: number;
  y: number;
  direction: Direction;
  plugType: PlugType;
  state: State;
}
