import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  HostBinding,
  Input,
  Output,
} from '@angular/core';

@Component({
  selector: 'app-custom-button',
  templateUrl: './custom-button.component.html',
  imports: [CommonModule],
})
export class CustomButtonComponent {
  @Input() label: string = 'Click';
  @Input() type: string = 'button';
  @Input() disabled?: boolean = false;
  @Input() color: 'dark-blue' | 'light-blue' | 'gray' | 'red' | 'green' | 'light-gray' =
    'dark-blue';
  @Input() extraClasses?: string = '';
  @Input() icon?: string = '';
  @Input() iconSize?: string = '24px';
  @Input() iconPosition: 'left' | 'right' = 'left';

  @Output() onClick = new EventEmitter<Event>();

  @HostBinding('style.backgroundColor') get backgroundColor() {
    return `var(--${this.color})`;
  }

  handleClick(event: Event) {
      this.onClick.emit(event);
  }

  get buttonClasses() {
    const baseClasses =
      'px-4 py-2 rounded-lg font-semibold transition-all duration-300 focus:outline-none text-white';
      const clickClass = !this.disabled ? 'cursor-pointer' : '';
    return `${baseClasses} ${this.extraClasses} ${clickClass}`;
  }

  get buttonStyle() {
    return {
      backgroundColor: `var(--${this.color})`,
    };
  }
}
