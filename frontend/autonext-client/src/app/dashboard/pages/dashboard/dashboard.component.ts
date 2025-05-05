import {
  Component,
  inject,
  ViewChild,
  ChangeDetectorRef,
  AfterViewInit,
} from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '@auth/services/auth.service';
import { HeaderComponent } from '@shared/header/header.component';
import { DashboardService } from '../../services/dashboard.service';
import { GraphicsComponent } from '../../components/graphics/graphics.component';
import { DashboardExportRequest } from '../../interfaces/graphics-request.interface';
import { CommonModule, NgClass } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  imports: [
    HeaderComponent,
    ReactiveFormsModule,
    GraphicsComponent,
    NgClass,
    CommonModule,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements AfterViewInit {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private dashboardService = inject(DashboardService);

  @ViewChild(GraphicsComponent) graphicsComponent!: GraphicsComponent;

  data = {};
  years: number[] = [];

  userName = this.authService.getName();

  graphicsReady = false;

  myForm = this.fb.group({
    month: [null],
    year: [new Date().getFullYear()],
  });

  months = [
    { id: 1, name: 'Enero' },
    { id: 2, name: 'Febrero' },
    { id: 3, name: 'Marzo' },
    { id: 4, name: 'Abril' },
    { id: 5, name: 'Mayo' },
    { id: 6, name: 'Junio' },
    { id: 7, name: 'Julio' },
    { id: 8, name: 'Agosto' },
    { id: 9, name: 'Septiembre' },
    { id: 10, name: 'Octubre' },
    { id: 11, name: 'Noviembre' },
    { id: 12, name: 'Diciembre' },
  ];

  constructor(private cdr: ChangeDetectorRef) {
    this.dashboardService.getAvailableYears().subscribe((res) => {
      this.years = res;
    });
    
    this.myForm.valueChanges.subscribe(({ month, year }) => {
      console.log('Formulario cambió:', month, year);
    });
  }

  ngAfterViewInit(): void {
    const waitForData = () => {
      if (this.graphicsComponent?.data) {
        this.graphicsReady = true;
        this.cdr.detectChanges();
      } else {
        setTimeout(waitForData, 50);
      }
    };

    waitForData();
  }

  exportDashboardPdf(): void {
    this.graphicsComponent.ensureChartsReady().then(() => {
      this.graphicsComponent.exportChartsAsBase64().then((charts) => {
        console.log('🔍 Charts capturados:', charts);

        const exportRequest: DashboardExportRequest = {
          daysPerMonthChart: charts.daysPerMonthChart,
          hoursPerMonthChart: charts.hoursPerMonthChart,
          avgDurationPerMonthChart: charts.avgDurationPerMonthChart,
          confirmationsChart: charts.confirmationsChart,
          cancelledChart: charts.cancelledChart,
          hoursPerWeekdayChart: charts.hoursPerWeekdayChart,

          month: this.myForm.value.month ?? null,
          year: this.myForm.value.year ?? new Date().getFullYear(),

          totalDaysReserved:
            this.graphicsComponent.data?.totalDaysReserved ?? 0,
          totalHoursReserved:
            this.graphicsComponent.data?.totalHoursReserved ?? 0,
          averageSessionDuration:
            this.graphicsComponent.data?.averageSessionDuration ?? 0,
          totalWeeklyHoursReserved:
            this.graphicsComponent.data?.weeklyHoursReserved?.reduce(
              (acc: any, item: { totalHours: any }) => acc + item.totalHours,
              0
            ) ?? 0,
          confirmedReservations:
            this.graphicsComponent.data?.confirmedReservations ?? 0,
          cancelledReservations:
            this.graphicsComponent.data?.cancelledReservations ?? 0,
        };

        this.dashboardService.exportDashboardPdf(exportRequest);
      });
    });
  }
}
