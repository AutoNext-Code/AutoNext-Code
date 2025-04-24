import { CommonModule } from "@angular/common";
import { Component, Input } from "@angular/core";
import {
  ApexAxisChartSeries,
  ApexChart,
  ApexDataLabels,
  ApexPlotOptions,
  ApexYAxis,
  ApexLegend,
  ApexStroke,
  ApexXAxis,
  ApexFill,
  ApexTooltip,
  NgApexchartsModule
} from "ng-apexcharts";

export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  dataLabels: ApexDataLabels;
  plotOptions: ApexPlotOptions;
  yaxis: ApexYAxis;
  xaxis: ApexXAxis;
  fill: ApexFill;
  tooltip: ApexTooltip;
  stroke: ApexStroke;
  legend: ApexLegend;
};

@Component({
  selector: 'app-chart',
  imports: [NgApexchartsModule, CommonModule],
  template: `
<apx-chart *ngIf="chartOptions"
  [series]="chartOptions.series!"
  [chart]="chartOptions.chart!"
  [dataLabels]="chartOptions.dataLabels!"
  [plotOptions]="chartOptions.plotOptions!"
  [yaxis]="chartOptions.yaxis!"
  [xaxis]="chartOptions.xaxis!"
  [fill]="chartOptions.fill!"
  [tooltip]="chartOptions.tooltip!"
  [stroke]="chartOptions.stroke!"
  [legend]="chartOptions.legend!">
</apx-chart>

  `,
  styleUrls: ['./chart.component.css']
})
export class ChartComponent {
  @Input() chartOptions!: Partial<ChartOptions>;
}