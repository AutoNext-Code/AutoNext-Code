export interface UserForAdmin {
  id: number;
  name: string;
  surname: string;
  email: string;
  strikes: number;
  role: 'Admin' | 'User' | 'Penalized' | 'Banned';
  jobPosition: string;
  workCenter: string;
}
