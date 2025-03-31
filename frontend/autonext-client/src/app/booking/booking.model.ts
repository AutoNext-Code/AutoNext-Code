export type Booking = {
  date: string;
  reservationTime: string;
  workcenter: string;
  parkingSpace: string;
  status: string;
  issues: string;
  canConfirm: boolean;
  canCancel: boolean;
  [key: string]: string | boolean;
};
