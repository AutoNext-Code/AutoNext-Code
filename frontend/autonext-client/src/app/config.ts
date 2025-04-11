import { enviromments } from '../environments/environments';

export const BASE_URL = enviromments.baseUrl;

export const API_BASE_URL = `${BASE_URL}api/`;

export const SWAGGER = `${BASE_URL}swagger-ui/index.html`;

/* ENDPOINTS DE AUTH */

export const LOGIN_ENDPOINT = `${API_BASE_URL}auth/login`;

export const REGISTER_ENDPOINT = `${API_BASE_URL}auth/register`;

export const CONFIRM_EMAIL_ENDPOINT = `${API_BASE_URL}auth/email-confirmation`;

/* ENDPOINTS DE MAP-Service  */

export const MAP_ENDPOINT = `${API_BASE_URL}parking/level`;

export const CENTERS_LEVELS = `${API_BASE_URL}parking/centers`

/* ENDPOINTS DE BOOKINGS */

export const BOOKINGS_ENDPOINT = `${API_BASE_URL}bookings`;

export const BOOKINGS_LIST_ENDPOINT = `${API_BASE_URL}bookings/booking-list`;

export const BOOKING_CANCEL_ENDPOINT = (id: number) => `${BOOKINGS_ENDPOINT}/${id}/cancel`;
export const BOOKING_CONFIRMATION_ENDPOINT = (id: number) => `${BOOKINGS_ENDPOINT}/${id}/confirmation`;

/* ENDPOINTS DE USER */

export const DATA_PROFILE_ENDPOINT = `${API_BASE_URL}user/profile`;

export const PASSWORD_CHANGE_ENDPOINT = `${API_BASE_URL}user/password-edit`;

export const CARS_USER_ENDPOINT = `${API_BASE_URL}cars`;

/* ENDPOINT PARA WORKCENTER */

export const CENTER_NAME_ENDPOINT = `${API_BASE_URL}parking/centers-names`;
