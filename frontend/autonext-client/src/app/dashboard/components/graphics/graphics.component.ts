import {
  AfterViewInit,
  Component,
  inject,
  Input,
  OnChanges,
  QueryList,
  SimpleChanges,
  ViewChildren,
} from '@angular/core';
import { DashboardService } from '../../services/dashboard.service';
import {
  CustomChartComponent
} from '../chart/custom-chart.component';

@Component({
  selector: 'app-graphics',
  standalone: true,
  imports: [CustomChartComponent],
  templateUrl: './graphics.component.html',
  styleUrls: ['./graphics.component.css'],
})
export class GraphicsComponent implements OnChanges, AfterViewInit {
  private dashboardService = inject(DashboardService);

  @Input() month: number | null = null;
  @Input() year: number = new Date().getFullYear();

  @ViewChildren(CustomChartComponent)
  chartComponents!: QueryList<CustomChartComponent>;

  data: any;

  public daysReservedChartOptions: Partial<ApexCharts.ApexOptions>;
  public hoursReservedChartOptions: Partial<ApexCharts.ApexOptions>;
  public avgDurationChartOptions: Partial<ApexCharts.ApexOptions>;
  public confirmedStatsChartOptions: Partial<ApexCharts.ApexOptions>;
  public unconfirmedStatsChartOptions: Partial<ApexCharts.ApexOptions>;

  constructor() {
    const defaultOptions: Partial<ApexCharts.ApexOptions> = {
      chart: { type: 'bar', height: 350 },
      plotOptions: { bar: { horizontal: false, columnWidth: '55%' } },
      dataLabels: { enabled: false },
      stroke: { show: true, width: 2, colors: ['transparent'] },
      xaxis: {
        categories: [
          'Enero',
          'Febrero',
          'Marzo',
          'Abril',
          'Mayo',
          'Junio',
          'Julio',
          'Agosto',
          'Septiembre',
          'Octubre',
          'Noviembre',
          'Diciembre',
        ],
      },
      yaxis: { title: { text: 'Valores' } },
      fill: { opacity: 1 },
      tooltip: { y: { formatter: (val: number) => `${val}` } },
    };

    this.daysReservedChartOptions = { ...defaultOptions, series: [] };
    this.hoursReservedChartOptions = { ...defaultOptions, series: [] };
    this.avgDurationChartOptions = { ...defaultOptions, series: [] };
    this.confirmedStatsChartOptions = { ...defaultOptions, series: [] };
    this.unconfirmedStatsChartOptions = { ...defaultOptions, series: [] };

    this.loadData();
  }

  ngAfterViewInit(): void {
    // Si necesitas algún delay inicial para renderizado
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['month'] || changes['year']) {
      this.loadData();
    }
  }

  private loadData() {
    this.dashboardService
      .getDashboardData(this.month, this.year)
      .subscribe((dashboardData) => {
        this.updateChartData(dashboardData);
        this.data = dashboardData;
  
        setTimeout(() => {
          this.chartComponents.forEach((chart) => {
            chart['renderChart']?.();
          });
        }, 100);
      });
  }
  

  private updateChartData(dashboardData: any) {
    this.daysReservedChartOptions.series = [
      {
        name: 'Días Reservados',
        data: dashboardData.monthlyDaysReserved.map((item: any) => item.value),
      },
    ];
    this.hoursReservedChartOptions.series = [
      {
        name: 'Horas Reservadas',
        data: dashboardData.monthlyHoursReserved.map((item: any) => item.value),
      },
    ];
    this.avgDurationChartOptions.series = [
      {
        name: 'Duración Promedio',
        data: dashboardData.monthlyAvgDuration.map((item: any) => item.value),
      },
    ];
    this.confirmedStatsChartOptions.series = [
      {
        name: 'Confirmados',
        data: dashboardData.monthlyConfirmationStats.map(
          (item: any) => item.confirmed
        ),
      },
    ];
    this.unconfirmedStatsChartOptions.series = [
      {
        name: 'No Confirmados',
        data: dashboardData.monthlyConfirmationStats.map(
          (item: any) => item.unconfirmed
        ),
      },
    ];
  }

  getMonthName(monthId: number | null): string {
    const months = [
      'Todos los meses',
      'Enero',
      'Febrero',
      'Marzo',
      'Abril',
      'Mayo',
      'Junio',
      'Julio',
      'Agosto',
      'Septiembre',
      'Octubre',
      'Noviembre',
      'Diciembre',
    ];
    return monthId === null ? 'Todos los meses' : months[monthId] ?? '';
  }

  async exportChartsAsBase64(): Promise<{
    daysPerMonthChart: string;
    hoursPerMonthChart: string;
    avgDurationPerMonthChart: string;
    hoursPerWeekdayChart: string;
    confirmationsChart: string;
  }> {
    const chartsArray = this.chartComponents?.toArray() ?? [];

    console.log('📊 Charts encontrados:', chartsArray.length);

    const getBase64 = async (chart: CustomChartComponent): Promise<string> => {
      return await chart.getBase64Image();
    };

    return {
      daysPerMonthChart: await getBase64(chartsArray[0]),
      hoursPerMonthChart: await getBase64(chartsArray[1]),
      avgDurationPerMonthChart: await getBase64(chartsArray[2]),
      confirmationsChart: await getBase64(chartsArray[3]),
      hoursPerWeekdayChart: await getBase64(chartsArray[4]),
    };
  }

  async ensureChartsReady(): Promise<void> {
    return new Promise((resolve) => {
      const check = () => {
        const charts = this.chartComponents.toArray();

        const allReady = charts.every((chartComp) => {
          return typeof chartComp.getBase64Image === 'function';
        });

        if (allReady) resolve();
        else setTimeout(check, 100);
      };

      check();
    });
  }
}
