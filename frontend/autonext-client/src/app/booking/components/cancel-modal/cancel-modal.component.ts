import { CommonModule } from '@angular/common';
import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';

@Component({
  selector: 'cancel-modal',
  imports: [CommonModule],
  templateUrl: './cancel-modal.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CancelModalComponent {
  @Input() visible: boolean = false;
  @Output() confirm = new EventEmitter<void>();
  @Output() cancel = new EventEmitter<void>();

  onConfirm() {
    this.confirm.emit();
  }

  onCancel() {
    this.cancel.emit();
  }
}
