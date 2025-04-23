import { Component, EventEmitter, inject, Input, Output } from '@angular/core';
 
import { SpaceData } from '@booking/interfaces/spaceData.interface';
import { BookingService } from '@booking/services/booking.service';
 
import { ErrorAnimationComponent } from '@shared/error-animation/error-animation.component';
import { CustomButtonComponent } from "@shared/components/ui/custom-button/custom-button.component";
import { ConfirmationAnimationComponent } from "@shared/confirmation-animation/confirmation-animation.component";

import { CommonModule, DatePipe } from '@angular/common';

import { AppComponent } from '../../../app.component';
import { CanBookResponse } from '@maps/interfaces/CanBookResponse';
 
@Component({
  selector: 'space-data',
  imports: [CustomButtonComponent, ConfirmationAnimationComponent, ErrorAnimationComponent, DatePipe, CommonModule],
  templateUrl: './space-data.component.html',
})
export class SpaceDataComponent {
 
  @Input({required: true})
  spaceData!: SpaceData ;

  @Input({required: true})
  canBook!: CanBookResponse ;
 
  @Output() modalEmitter = new EventEmitter<void>() ;
  @Output() reservationMade = new EventEmitter<void>();
 
  private appComponent: AppComponent = inject(AppComponent);
 
  clicked = false ;
 
  confirmation = true ;
  error = true ;
 
  public bookingService: BookingService = inject(BookingService) ;
 
  closeModal() {
    this.modalEmitter.emit();
  }
 
  async postBooking(params: SpaceData) {
    if(!this.clicked) {
 
      this.clicked = true ;
      let animation = await this.bookingService.createBooking(params)
      this.tickAnimation(animation.success) ;    
     
      if (!animation.success) {
        this.appComponent.showToast(
          'error',
          'Reserva fallida',
          animation.errorMsg ?? 'No se pudo completar la reserva',
          3000,
          75
        );
      }
     
    }
  }
 
 
  tickAnimation(animation: boolean) {
    if(animation){
      this.confirmation = false ;
    }else {
      this.error = false ;
    }
    setTimeout(() => {
      if(animation){
        this.confirmation = true ;
        this.reservationMade.emit();
      }else {
        this.error = true ;
      }
      this.closeModal() ;
      this.clicked = false ;
    }, 1500)
  }
 
}