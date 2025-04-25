export interface GraphicsRequest {
  strikes: number;
  penalized: boolean;
  totalDaysReserved: number;
  totalHoursReserved: number;
  averageSessionDuration: number;
  confirmedReservations: number;
  unconfirmedReservations: number;
  monthlyDaysReserved: Monthly[];
  monthlyHoursReserved: Monthly[];
  monthlyAvgDuration: Monthly[];
  monthlyConfirmationStats: MonthlyConfirmationStat[];
  weeklyHoursReserved: WeeklyHoursReserved[];
}

export interface Monthly {
  month?: string;
  value: number;
}

export interface MonthlyConfirmationStat {
  month: string;
  confirmed: number;
  unconfirmed: number;
}

export interface WeeklyHoursReserved {
  day: string;
  totalHours: number;
}

export interface DashboardExportRequest {
  month: number | null;
  year: number;
  daysPerMonthChart: string;
  hoursPerMonthChart: string;
  avgDurationPerMonthChart: string;
  hoursPerWeekdayChart: string;
  confirmationsChart: string;
  cancelledChart: string;
  
  totalDaysReserved: number;
  totalHoursReserved: number;
  averageSessionDuration: number;
  totalWeeklyHoursReserved: number;
  confirmedReservations: number;
  cancelledReservations: number;
}
