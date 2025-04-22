import { inject, Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { CentersMaps } from '@maps/interfaces/CentersMaps.interface';
import { SpaceData } from '@booking/interfaces/spaceData.interface';
import { PlugType } from '@maps/enums/plugType.enum';
import { DataRequestService } from '@maps/services/data-request.service';
import { BookingFormUtils } from '../../../utils/bookingForm-utils';

@Injectable({
    providedIn: 'root',
})
export class BookingFormService {

    bookingFormUtils = BookingFormUtils;
    private dataRequestService: DataRequestService = inject(DataRequestService);

    updateParkingLevels(centerName: string, maps: CentersMaps[], form: FormGroup): CentersMaps['parkingLevels'] {
        const center = maps.find((c) => c.centerName === centerName);
        const parkingLevels = center?.parkingLevels || [];
        form.get('level')?.setValue(parkingLevels.length ? parkingLevels[0].id : 0);
        return parkingLevels;
    }

    updatePlugTypes(selectedCar: any): (string | PlugType)[] {
        if (selectedCar && PlugType[selectedCar.plugType as keyof typeof PlugType] === PlugType.Undefined) {
            return Object.values(PlugType).filter((value) => typeof value === 'string' && value !== 'NoType');
        }
        return [selectedCar?.plugType];
    }

    setData(form: FormGroup, levelString: string): void {
        const data: SpaceData = {
            workCenter: form.value['center'],
            date: form.value['date'],
            level: levelString || '1',
            levelId: form.value['level'],
            car: form.value['selectedCar']?.carPlate || '',
            carId: form.value['selectedCar']?.id || 0,
            startTime: form.value['startHour'],
            endTime: form.value['endHour'],
        };
        this.dataRequestService.setData(data);
    }

}
