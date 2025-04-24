import { Component, inject, Input, OnChanges, SimpleChanges } from "@angular/core";
import { DashboardService } from "../../services/dashboard.service";
import { ChartComponent, ChartOptions } from "../chart/chart.component";
import { DashboardDTO } from "../../models/dashboard.model";

@Component({
  selector: 'app-graphics',
  imports: [ChartComponent],
  templateUrl: './graphics.component.html',
  styleUrls: ['./graphics.component.css']
})
export class GraphicsComponent implements OnChanges {
  private dashboardService = inject(DashboardService);

  @Input() month: number = 0;
  @Input() year: number = 2025;

  data: any;

  public daysReservedChartOptions: Partial<ChartOptions>;
  public hoursReservedChartOptions: Partial<ChartOptions>;
  public avgDurationChartOptions: Partial<ChartOptions>;
  public confirmedStatsChartOptions: Partial<ChartOptions>;
  public unconfirmedStatsChartOptions: Partial<ChartOptions>;

  constructor() {
    // Default chart options
    const defaultOptions: Partial<ChartOptions> = {
      chart: { type: "bar", height: 350 },
      plotOptions: { bar: { horizontal: false, columnWidth: "55%" } },
      dataLabels: { enabled: false },
      stroke: { show: true, width: 2, colors: ["transparent"] },
      xaxis: { categories: ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"] },
      yaxis: { title: { text: "Valores" } },
      fill: { opacity: 1 },
      tooltip: { y: { formatter: (val: number) => `${val}` } }
    };

    // Initialize with empty series
    this.daysReservedChartOptions = { ...defaultOptions, series: [] };
    this.hoursReservedChartOptions = { ...defaultOptions, series: [] };
    this.avgDurationChartOptions = { ...defaultOptions, series: [] };
    this.confirmedStatsChartOptions = { ...defaultOptions, series: [] };
    this.unconfirmedStatsChartOptions = { ...defaultOptions, series: [] };

    // Fetch data from the service
    this.loadData();
  }

  updateChartData(dashboardData: any) {
    this.daysReservedChartOptions.series = [{ name: "Días Reservados", data: dashboardData.monthlyDaysReserved.map((item: any) => item.value) }];
    this.hoursReservedChartOptions.series = [{ name: "Horas Reservadas", data: dashboardData.monthlyHoursReserved.map((item: any) => item.value) }];
    this.avgDurationChartOptions.series = [{ name: "Duración Promedio", data: dashboardData.monthlyAvgDuration.map((item: any) => item.value) }];
    this.confirmedStatsChartOptions.series = [{ name: "Confirmados", data: dashboardData.monthlyConfirmationStats.map((item: any) => item.confirmed) }];
    this.unconfirmedStatsChartOptions.series = [{ name: "No Confirmados", data: dashboardData.monthlyConfirmationStats.map((item: any) => item.unconfirmed) }];
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['month'] || changes['year']) {
      this.loadData();
    }
  }

  private loadData() {
    this.dashboardService.getDashboardData(this.month, this.year).subscribe((dashboardData) => {
      this.updateChartData(dashboardData);
      this.data = dashboardData;
    });
  }

  getMonthName(monthId: number): string {
    const months = [
      "Todos los meses", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
      "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    ];
    return months[monthId];
  }

}