import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '@auth/services/auth.service';
import { HeaderComponent } from '@shared/header/header.component';
import { DashboardDTO } from '../../models/dashboard.model';
import { DashboardService } from '../../services/dashboard.service';
import { GraphicsComponent } from '../../components/graphics/graphics.component';

@Component({
  selector: 'app-dashboard',
  imports: [HeaderComponent, ReactiveFormsModule, GraphicsComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private dashboardService = inject(DashboardService);

  data = {};

  userName = this.authService.getName();


  myForm = this.fb.group({
    month: [0],
    year: [0],
  })


  months = [
    { id: 1, name: "Enero" },
    { id: 2, name: "Febrero" },
    { id: 3, name: "Marzo" },
    { id: 4, name: "Abril" },
    { id: 5, name: "Mayo" },
    { id: 6, name: "Junio" },
    { id: 7, name: "Julio" },
    { id: 8, name: "Agosto" },
    { id: 9, name: "Septiembre" },
    { id: 10, name: "Octubre" },
    { id: 11, name: "Noviembre" },
    { id: 12, name: "Diciembre" },
  ]

  constructor() {
    this.myForm.valueChanges.subscribe(({ month, year }) => {
      console.log("Formulario cambió:", month, year);
      // Aquí podrías hacer algo adicional si lo necesitas
    });
  }
  



}
