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
import { CustomChartComponent } from '../chart/custom-chart.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-graphics',
  standalone: true,
  imports: [CustomChartComponent, CommonModule],
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

    this.weeklyHoursChartOptions.series = [
      {
        name: 'Horas por día',
        data: dashboardData.weeklyHoursReserved.map(
          (item: any) => item.totalHours
        ),
      },
    ];

    this.weeklyHoursChartOptions.xaxis = {
      categories: dashboardData.weeklyHoursReserved.map(
        (item: any) => item.day
      ),
    };
  }

  public weeklyHoursChartOptions: Partial<ApexCharts.ApexOptions> = {
    chart: { type: 'bar', height: 350 },
    plotOptions: { bar: { horizontal: false, columnWidth: '55%' } },
    dataLabels: { enabled: false },
    stroke: { show: true, width: 2, colors: ['transparent'] },
    xaxis: { categories: [] }, // lo rellenamos luego
    yaxis: { title: { text: 'Horas' } },
    fill: { opacity: 1 },
    tooltip: { y: { formatter: (val: number) => `${val}` } },
    series: [],
  };

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
    unconfirmedChart: string;
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
      hoursPerWeekdayChart: await getBase64(chartsArray[3]),
      confirmationsChart: await getBase64(chartsArray[4]),
      unconfirmedChart: await getBase64(chartsArray[5]),
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

  getTotalWeeklyHours(): number {
    return (
      this.data?.weeklyHoursReserved?.reduce(
        (acc: number, item: any) => acc + item.totalHours,
        0
      ) ?? 0
    );
  }

  hasNoData(): boolean {
    if (!this.data) return true;

    const totals = [
      this.data.totalDaysReserved,
      this.data.totalHoursReserved,
      this.data.averageSessionDuration,
      this.data.confirmedReservations,
      this.data.unconfirmedReservations,
      this.getTotalWeeklyHours(),
    ];

    return totals.every(
      (val) => val === 0 || val === null || val === undefined
    );
  }

  getEmptyDataMessage(): string {
    const monthName = this.getMonthName(this.month);
    return this.month
      ? `No hay datos disponibles para el mes de ${monthName.toLowerCase()} del año  ${this.year}.`
      : 'Todavía no hay datos disponibles para mostrar.';
  }  
}