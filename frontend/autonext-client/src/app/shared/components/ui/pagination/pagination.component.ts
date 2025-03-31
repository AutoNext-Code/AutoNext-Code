import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'pagination',
  imports: [CommonModule],
  templateUrl: './pagination.component.html',
  styleUrl: './pagination.component.css'
})

export class PaginationComponent {
  @Input() currentPage = 1;
  @Input() totalPages = 1;
  @Output() pageChange = new EventEmitter<number>();

  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.pageChange.emit(page);
    }
  }

  buttons = [
    {
      icon: 'keyboard_double_arrow_left',
      goTo: () => this.goToPage(1),
      disabled: () => this.currentPage === 1
    },
    {
      icon: 'chevron_left',
      goTo: () => this.goToPage(this.currentPage - 1),
      disabled: () => this.currentPage === 1
    },
    {
      icon: 'chevron_right',
      goTo: () => this.goToPage(this.currentPage + 1),
      disabled: () => this.currentPage === this.totalPages
    },
    {
      icon: 'keyboard_double_arrow_right',
      goTo: () => this.goToPage(this.totalPages),
      disabled: () => this.currentPage === this.totalPages
    }
  ];
}
