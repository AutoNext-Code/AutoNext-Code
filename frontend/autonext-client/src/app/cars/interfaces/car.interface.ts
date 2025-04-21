import { PlugType } from "@maps/enums/plugType.enum";

export interface CarDto {
    id?: number;
    carPlate: string;
    name: string;
    plugType: PlugType;
}
