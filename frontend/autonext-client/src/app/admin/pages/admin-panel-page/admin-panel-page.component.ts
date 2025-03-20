import { Component } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

import { AdminCard } from '../../components/admin-card/interfaces/card.interface';
import { AdminCardComponent } from '../../components/admin-card/admin-card.component';

@Component({
  imports: [AdminCardComponent],
  templateUrl: './admin-panel-page.component.html',
  styleUrl: './admin-panel-page.component.css'
})
export class AdminPanelPageComponent {

  public menuItems: AdminCard[];

  constructor(private sanitizer: DomSanitizer) {
    this.menuItems = [
      { content: 'Métricas Y estadisticas', url: '/admin-home/dashboard', title: 'Dashboard', icon: 'dashboard' },
      { content: 'Gestión de Reportes y Eventos', url: '/admin-home/incidents', title: 'Incidencias', icon: 'report_problem' },
      { content: 'Configurar y Administrar las plazas', url: '/admin-home/place-management', title: 'Gestión de plazas', icon: 'ev_station' },
      { content: 'Configurar y Administrar los Delegaciones', url: '/admin-home/delegation-management', title: 'Gestión de delegaciones', icon: 'roofing' },
      { content: 'Configurar y Administrar los Usuarios', url: '/admin-home/user-management', title: 'Gestión de usuarios', icon: 'supervised_user_circle' }
    ]
  }

  private sanitizeHtml(html: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

}
