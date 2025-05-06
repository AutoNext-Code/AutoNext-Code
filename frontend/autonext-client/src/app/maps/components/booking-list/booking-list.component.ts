import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

import { BookingList } from '@maps/interfaces/bookingList.interface';
import { PaginationComponent } from "../../../shared/components/ui/pagination/pagination.component";

@Component({
  selector: 'booking-list',
  imports: [
    DatePipe,
    PaginationComponent
],
  templateUrl: './booking-list.component.html',
  styleUrl: './booking-list.component.css'
})
export class BookingListComponent {

  @Input({required: true})
  bookingList!: BookingList ;

  currentPage: number = 1 ;

  @Output()
  pageChanger: EventEmitter<number> = new EventEmitter() ;

  pageChange(change: number): void {
    this.currentPage = change ;
    this.pageChanger.emit(change) ; 
  }

}
