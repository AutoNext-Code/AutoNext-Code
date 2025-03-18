import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ICONS } from '../../../shared/icons';

@Component({
  selector: 'admin-sidebar',
  imports: [CommonModule, RouterModule],
  templateUrl: './side-bar.component.html',
  styleUrl: './side-bar.component.css'
})
export class SideBarComponent {

  public menuItems: { route: string; name: string; icon: SafeHtml }[];

  @Output() sidebarToggled = new EventEmitter<boolean>();
  isSidebarOpen = true;

  constructor(private sanitizer: DomSanitizer) {
    this.menuItems = [
      { route: '/admin-home/panel-admin', name: 'Panel de Administrador', icon: this.sanitizeHtml(ICONS.panelAdmin) },
      { route: '/admin-home/dashboard', name: 'Dashboard', icon: this.sanitizeHtml(ICONS.dashboard) },
      { route: '/admin-home/incidents', name: 'Incidencias', icon: this.sanitizeHtml(ICONS.incidents) },
      { route: '/admin-home/place-management', name: 'Gestión de plazas', icon: this.sanitizeHtml(ICONS.stations) },
      { route: '/admin-home/delegation-management', name: 'Gestión de delegaciones', icon: this.sanitizeHtml(ICONS.roofing) },
      { route: '/admin-home/user-management', name: 'Gestión de usuarios', icon: this.sanitizeHtml(ICONS.supervisedUser) }
    ]
  }

  private sanitizeHtml(html: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
    this.sidebarToggled.emit(this.isSidebarOpen);
  }

}