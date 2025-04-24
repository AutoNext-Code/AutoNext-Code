import { Component, inject, ViewChild } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '@auth/services/auth.service';
import { HeaderComponent } from '@shared/header/header.component';
import { DashboardService } from '../../services/dashboard.service';
import { GraphicsComponent } from '../../components/graphics/graphics.component';
import { DashboardExportRequest } from '../../interfaces/graphics-request.interface';

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

  @ViewChild(GraphicsComponent) graphicsComponent!: GraphicsComponent;

  data = {};

  userName = this.authService.getName();


  myForm = this.fb.group({
    month: [null],
    year: [new Date().getFullYear()],
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
    });
  }
  
  
  exportDashboardPdf(): void {
    this.graphicsComponent.ensureChartsReady().then(() => {
      this.graphicsComponent.exportChartsAsBase64().then((charts) => {
        console.log("🔍 Charts capturados:", charts);
    
        const exportRequest: DashboardExportRequest = {
          daysPerMonthChart: charts.daysPerMonthChart,
          hoursPerMonthChart: charts.hoursPerMonthChart,
          avgDurationPerMonthChart: charts.avgDurationPerMonthChart,
          hoursPerWeekdayChart: charts.hoursPerWeekdayChart,
          confirmationsChart: charts.confirmationsChart,
          month: this.myForm.value.month ?? null,
          year: this.myForm.value.year ?? new Date().getFullYear(),
        };
    
        this.dashboardService.exportDashboardPdf(exportRequest);
      });
    });
    
  }
  
}
