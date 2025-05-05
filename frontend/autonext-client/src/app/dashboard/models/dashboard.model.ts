export interface DashboardDTO {
  strikes: number;
  penalized: boolean;
  totalDaysReserved: number;
  totalHoursReserved: number;
  averageSessionDuration: number;
  confirmedReservations: number;
  unconfirmedReservations: number;

  monthlyDaysReserved: MonthlyMetricDto[];
  monthlyHoursReserved: MonthlyMetricDto[];
  monthlyAvgDuration: MonthlyMetricDto[];

  monthlyConfirmationStats: MonthlyConfirmationDto[];
  weeklyHoursReserved: WeekdayMetricDto[];
}

export interface MonthlyMetricDto {
  month: string; // Ej: "Abril"
  value: number;
}

export interface MonthlyConfirmationDto {
  month: string;
  confirmed: number;
  unconfirmed: number;
}

export interface WeekdayMetricDto {
  day: string; // Ej: "Lunes"
  totalHours: number;
}
