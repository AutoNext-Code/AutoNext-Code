import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { HeaderComponent } from '@shared/header/header.component';

import { SideBarComponent } from '../../components/side-bar/side-bar.component';

@Component({
  imports: [HeaderComponent, SideBarComponent, RouterModule, CommonModule],
  templateUrl: './home-admin-page.component.html',
  styleUrl: './home-admin-page.component.css'
})
export class HomeAdminPageComponent {

  isSidebarOpen = true;

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

}
