import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'admin-sidebar',
  imports: [CommonModule],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent {

  isSidebarOpen = true; 

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

}