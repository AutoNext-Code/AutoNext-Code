import { Component, inject } from '@angular/core';
import { AuthService } from '@auth/services/auth.service';

@Component({
  selector: 'user-dashboard-layout',
  imports: [],
  templateUrl: './dashboard-layout.component.html',
  styleUrl: './dashboard-layout.component.css'
})
export class DashboardLayoutComponent {

  private authService = inject(AuthService);

  userName = this.authService.getName();

  months = [
    {id: 1, name: "Enero"     },
    {id: 2, name: "Febrero"     },
    {id: 3, name: "Marzo"     },
    {id: 4, name: "Abril"     },
    {id: 5, name: "Mayo"      },
    {id: 6, name: "Junio"     },
    {id: 7, name: "Julio"     },
    {id: 8, name: "Agosto"    },
    {id: 9, name: "Septiembre"},
    {id: 10, name: "Octubre"   },
    {id: 11, name: "Noviembre" },
    {id: 12, name: "Diciembre" },
  ]

}
