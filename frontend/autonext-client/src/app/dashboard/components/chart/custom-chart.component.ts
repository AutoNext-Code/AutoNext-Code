import {
  Component,
  Input,
  ElementRef,
  ViewChild,
  AfterViewInit,
} from '@angular/core';
import ApexCharts, { ApexOptions } from 'apexcharts';

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
  selector: 'app-custom-chart',
  standalone: true,
  template: `<div #chartContainer></div>`,
  styleUrls: ['./chart.component.css'],
})
export class CustomChartComponent implements AfterViewInit {
  @Input() chartOptions!: Partial<ApexOptions>;
  @ViewChild('chartContainer', { static: true })
  chartContainer!: ElementRef<HTMLDivElement>;

  private apexChartInstance: ApexCharts | null = null;

  ngAfterViewInit(): void {
    if (this.chartOptions) {
      this.renderChart();
    }
  }

  private async renderChart() {
    if (this.apexChartInstance) {
      await this.apexChartInstance.destroy();
    }

    this.apexChartInstance = new ApexCharts(
      this.chartContainer.nativeElement,
      this.chartOptions as any
    );

    await this.apexChartInstance.render();
  }

  async getBase64Image(): Promise<string> {
    if (!this.apexChartInstance) {
      console.warn('⚠️ ApexCharts instance no disponible');
      return '';
    }

    const result = await this.apexChartInstance.dataURI();

    if ('imgURI' in result) {
      return result.imgURI;
    }

    if ('blob' in result) {
      return await this.blobToBase64(result.blob);
    }

    return '';
  }

  private async blobToBase64(blob: Blob): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onloadend = () => resolve(reader.result as string);
      reader.onerror = reject;
      reader.readAsDataURL(blob);
    });
  }
}
