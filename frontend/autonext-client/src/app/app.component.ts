import { Component, ViewChild } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { ToastComponent } from '@shared/toast/toast.component';

@Component({
  selector: 'app-root',
  imports: [RouterModule, RouterOutlet, ToastComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  
  @ViewChild(ToastComponent) toastComponent!: ToastComponent;


  showToast(severity: 'success' | 'error' | 'warn' , summary: string, detail: string, life: number = 3000, topOffset?: number) {
    this.toastComponent.showToast(severity, summary, detail, life, topOffset);
  }
}
