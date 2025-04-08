import { CommonModule } from '@angular/common';
import { Component, inject, Input } from '@angular/core';

interface ToastMessage {
  severity: 'success' | 'warn' | 'error';
  summary: string;
  detail: string;
  life?: number;
  topOffset?: number;
}

@Component({
  selector: 'app-toast',
  imports: [CommonModule],
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css'],
})
export class ToastComponent {

  messages: ToastMessage[] = [];

  showToast(
    severity: 'success' | 'warn' | 'error',
    summary: string,
    detail: string,
    life: number = 3000,
    topOffset?: number
  ) {
    const exists = this.messages.some(
      (msg) =>
        msg.severity === severity &&
        msg.summary === summary &&
        msg.detail === detail
    );

    if (!exists) {
      const toast: ToastMessage = { severity, summary, detail, life };

      if (topOffset !== undefined) {

        toast.topOffset = topOffset;

      }

      this.messages.push(toast);

      setTimeout(() => {
        this.messages = this.messages.filter((msg) => msg !== toast);
      }, life);
    }
  }

  getIcon(severity: string): string {
    switch (severity) {
      case 'success':
        return 'check_circle';
      case 'error':
        return 'error';
      case 'warn':
        return 'warning';
      default:
        return 'notifications';
    }
  }
}
