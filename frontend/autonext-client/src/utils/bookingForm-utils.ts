import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export class BookingFormUtils {

    static startHours: string[] = [
        '08:00',
        '08:30',
        '09:00',
        '09:30',
        '10:00',
        '10:30',
        '11:00',
        '11:30',
        '12:00',
        '12:30',
        '13:00',
        '13:30',
        '14:00',
        '14:30',
        '15:00',
        '15:30',
        '16:00',
        '16:30',
        '17:00',
        '17:30',
        '18:00',
        '18:30',
        '19:00',
        '19:30',
    ];

    static endHours: string[] = [
        '08:30',
        '09:00',
        '09:30',
        '10:00',
        '10:30',
        '11:00',
        '11:30',
        '12:00',
        '12:30',
        '13:00',
        '13:30',
        '14:00',
        '14:30',
        '15:00',
        '15:30',
        '16:00',
        '16:30',
        '17:00',
        '17:30',
        '18:00',
        '18:30',
        '19:00',
        '19:30',
        '20:00',
    ];

    /**
     * Valida un rango de horas, devuelve un validador que puede ser utilizado en formularios de Angular
     * @returns 
     */
    static validateHourRange(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            const start = control.get('startHour')?.value;
            const end = control.get('endHour')?.value;

            if (!start || !end) {
                return null;
            }

            if (start >= end) {
                return { invalidRange: true };
            }

            return null;
        };
    }

    /**
     * Redondea una fecha a la media hora más cercana
     * @param date 
     * @returns 
     */
    static roundToNearestHalfHour(date: Date = new Date(), offsetMillis: number = 0): string {
        const adjustedDate = new Date(date.getTime() + offsetMillis);
        const minutes = adjustedDate.getMinutes();
        let hour = adjustedDate.getHours();
        let roundedMinutes;
    
        if (minutes >= 30) {
          hour += 1;
          roundedMinutes = 0;
        } else {
          roundedMinutes = 30;
        }
    
        const h = hour < 10 ? '0' + hour : hour;
        return `${h}:${roundedMinutes}`;
      }

}