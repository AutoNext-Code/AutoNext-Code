import { Injectable } from '@angular/core';

import { FormGroup } from '@angular/forms';
import { BookingFormUtils } from '../../../utils/bookingForm-utils';

@Injectable({ providedIn: 'root' })
export class DateUtilsService {

  bookingFormUtils = BookingFormUtils;

  updateHoursForToday(myForm: FormGroup, currentTime: string): { filteredStartHours: string[]; filteredEndHours: string[] } {
    const filteredStartHours = this.bookingFormUtils.startHours.filter(
      (hour) => hour >= currentTime
    );
    const filteredEndHours = this.bookingFormUtils.endHours.filter(
      (hour) => hour > currentTime
    );

    console.log(currentTime, 'eres tu')

    myForm.patchValue(
      {
        startHour: filteredStartHours[0] || '',
        endHour: filteredEndHours[0] || '',
      },
      { emitEvent: false }
    );

    return { filteredStartHours, filteredEndHours };
  }

  resetHoursForDefault(myForm: FormGroup): { filteredStartHours: string[]; filteredEndHours: string[] } {
    const filteredStartHours = [...this.bookingFormUtils.startHours];
    const filteredEndHours = [...this.bookingFormUtils.endHours];

    myForm.patchValue(
      { startHour: '08:00', endHour: '17:00' },
      { emitEvent: false }
    );

    return { filteredStartHours, filteredEndHours };
  }

  getDate(days?: number): string {
    const date = new Date();
    if (days) date.setDate(date.getDate() + days);
    return `${date.getFullYear()}-${(date.getMonth() + 1)
      .toString()
      .padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
  }

  isSelectedDateToday(selectedDate: string): boolean {
    const today = this.getDate(0);
    const selected = new Date(selectedDate).toISOString().split('T')[0];
    return selected === today;
  }

}