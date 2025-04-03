import { PlugType } from "@maps/enums/PlugType.enum";

export interface CarDto {
    id: number;
    carPlate: string;
    name: String;
    plugType: PlugType;
}