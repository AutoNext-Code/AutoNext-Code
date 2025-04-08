import { Component, EventEmitter, inject, Input, Output } from '@angular/core';

import { SpaceData } from '@booking/interfaces/spaceData.interface';
import { BookingService } from '@booking/services/booking.service';

import { CustomButtonComponent } from "@shared/components/ui/custom-button/custom-button.component";
import { ConfirmationAnimationComponent } from "../../../shared/confirmation-animation/confirmation-animation.component";
import { ErrorAnimationComponent } from '@shared/error-animation/error-animation.component';
import { CommonModule, DatePipe } from '@angular/common';
import { ToastComponent } from '@shared/toast/toast.component';
import { AppComponent } from '../../../app.component';

@Component({
  selector: 'space-data',
  imports: [CustomButtonComponent, ConfirmationAnimationComponent, ErrorAnimationComponent, DatePipe, CommonModule],
  templateUrl: './space-data.component.html',
})
export class SpaceDataComponent {

  @Input({required: true})
  spaceData!: SpaceData ;

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
      const result = await this.bookingService.createBooking(params);
      
      this.tickAnimation(result.success);
      
      if (!result.success) {
        this.appComponent.showToast(
          'error',
          'Reserva fallida',
          result.errorMsg ?? 'No se pudo completar la reserva',
          3000,
          75
        );
      }
      
      if (result.success) this.reservationMade.emit();
      
    }
  }
  
  
  tickAnimation(success: boolean) {
    if(success){
      this.confirmation = false ;
    }else {
      this.error = false ;
    }
    setTimeout(() => {
      if(success){
        this.confirmation = true ;
        this.reservationMade.emit(); 
      }else {
        this.error = true ;
      }
      this.closeModal() ;
      this.clicked = true ;
    }, 1500)
  }

}
