import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'shared-custom-modal',
  imports: [CommonModule],
  templateUrl: './custom-modal.component.html',
  styleUrl: './custom-modal.component.css',
})
export class CustomModalComponent {
  @Input() title: string = '';
  @Input() visible: boolean = false;
  @Input() container: any;
  @Input() buttons: any;
  @Input() widthClass: string = 'sm:max-w-lg w-full';
  @Input() height: string = 'auto'; 
  @Output() confirm = new EventEmitter<void>();
  @Output() cancel = new EventEmitter<void>();

  onConfirm() {
    this.confirm.emit();
  }

  onCancel() {
    this.cancel.emit();
  }
}
