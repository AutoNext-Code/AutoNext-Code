export interface BookingDTO {
  id: number;
  date: Date | null;
  startTime: string | null;
  endTime: string | null;
  userName: string | null;
  nameSpace: string | null;     
  delegation: string | null;     
  status: string | null;
  issues?: string | null;
  confirmationStatus: string | null;
  carName: string | null;
}
