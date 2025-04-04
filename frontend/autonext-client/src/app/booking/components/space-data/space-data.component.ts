import { Component, EventEmitter, inject, Input, Output } from '@angular/core';

import { SpaceData } from '@booking/interfaces/spaceData.interface';
import { BookingService } from '@booking/services/booking.service';

import { CustomButtonComponent } from "@shared/components/ui/custom-button/custom-button.component";
import { ConfirmationAnimationComponent } from "../../../shared/confirmation-animation/confirmation-animation.component";
import { ErrorAnimationComponent } from '@shared/error-animation/error-animation.component';

@Component({
  selector: 'space-data',
  imports: [CustomButtonComponent, ConfirmationAnimationComponent, ErrorAnimationComponent],
  templateUrl: './space-data.component.html',
  styleUrl: './space-data.component.css'
})
export class SpaceDataComponent {

  @Input({required: true})
  spaceData!: SpaceData ;

  @Output()
  modalEmitter = new EventEmitter<void>() ;

  animation = true ;

  public bookingService: BookingService = inject(BookingService) ;
  
  closeModal() {
    this.modalEmitter.emit();
  }

  postBooking(params: SpaceData) {
    this.bookingService.postBooking(params)
    this.checkAnimation() ;
  }
  
  checkAnimation() {
    this.animation = false ;
    setTimeout(() => {
      this.animation = true ;
      this.closeModal() ;
    }, 1500)
  }

}
