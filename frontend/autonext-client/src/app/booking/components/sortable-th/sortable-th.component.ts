import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Booking } from '../../booking.model';

@Component({
  selector: 'th[sortable-th]',
  imports: [],
  templateUrl: './sortable-th.component.html',
  styleUrl: './sortable-th.component.css',
  host: {
    'class': 'px-4 py-2 text-center border-r border-r-white cursor-pointer select-none'
  }
})
export class SortableThComponent {
  @Input() label: string = '';
  @Input() column: string = '';
  @Input() activeSortColumn: keyof Booking | string | null = null;
  @Input() sortDirection: 'asc' | 'desc' | null = null;

  @Output() sort = new EventEmitter<string>();

  onClick() {
    this.sort.emit(this.column);
  }

  get icon(): string {
    if (this.activeSortColumn !== this.column) return 'swap_vert';
    return this.sortDirection === 'asc' ? 'arrow_upward' : 'arrow_downward';
  }
}
