import { enviromments } from '../environments/environments';

export const BASE_URL = enviromments.baseUrl;

export const API_BASE_URL = `${BASE_URL}api/`;

export const SWAGGER = `${BASE_URL}swagger-ui/index.html`;

/* ENDPOINTS DE AUTH */

export const LOGIN_ENDPOINT = `${API_BASE_URL}auth/login`;
export const REGISTER_ENDPOINT = `${API_BASE_URL}auth/register`;

export const CONFIRM_EMAIL_ENDPOINT = `${API_BASE_URL}auth/email-confirmation`;

export const FORGET_PASSWORD = `${API_BASE_URL}auth/forget-password`;
export const RESET_PASSWORD = (token: string) => `${API_BASE_URL}auth/reset-password/${token}`;

/* ENDPOINTS DE MAP-Service  */

export const MAP_ENDPOINT = `${API_BASE_URL}parking/level`;

export const CENTERS_LEVELS = `${API_BASE_URL}parking/centers`

/* ENDPOINTS DE BOOKINGS */

export const BOOKINGS_ENDPOINT = `${API_BASE_URL}bookings`;
export const BOOKINGS_LIST_ENDPOINT = `${API_BASE_URL}bookings/booking-list`;
export const BOOKINGS_USER_CHECK = `${API_BASE_URL}bookings/can-book`;

export const BOOKING_CANCEL_ENDPOINT = (id: number) => `${BOOKINGS_ENDPOINT}/${id}/cancel`;
export const BOOKING_CONFIRMATION_ENDPOINT = (id: number) => `${BOOKINGS_ENDPOINT}/${id}/confirmation`;

/* ENDPOINTS DE USER */

export const DATA_PROFILE_ENDPOINT = `${API_BASE_URL}user/profile`;
export const PROFILE_EDIT_ENDPOINT = `${API_BASE_URL}user/edit`;
export const PASSWORD_CHANGE_ENDPOINT = `${API_BASE_URL}user/password-edit`;
export const CARS_USER_ENDPOINT = `${API_BASE_URL}cars`;

/* ENDPOINT PARA WORKCENTER */

export const CENTER_NAME_ENDPOINT = `${API_BASE_URL}parking/centers-names`;

/* ENDPOINT PARA DASHBOARD */ 

export const DASHBOARD = `${API_BASE_URL}dashboard`;
export const DASHBOARD_PDF = `${API_BASE_URL}dashboard/export`;
export const DASHBOARD_YEARS = `${API_BASE_URL}dashboard/years`;

/* ENDPOINTS PARA ADMINISTRADOR */

export const GET_USERS = `${API_BASE_URL}admin/user/get-users`;
export const TOGGLE_ROLE = (id: number) =>  `${API_BASE_URL}admin/user/toggle-admin-role/${id}`;
export const UPDATE_JOB_POSITION = (id: number) => `${API_BASE_URL}admin/update-job-position/${id}`;
export const UPDATE_WORK_CENTER = (id: number) => `${API_BASE_URL}admin/update-work-center/${id}`;