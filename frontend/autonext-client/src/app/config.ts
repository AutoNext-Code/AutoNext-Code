import { enviromments } from "../environments/environments";

export const BASE_URL = enviromments.baseUrl;

export const API_BASE_URL = `${BASE_URL}api/`;

export const SWAGGER = `${BASE_URL}swagger-ui/index.html`

// PRUEBA

export const PRUEBA = `${API_BASE_URL}`;

/* ENDPOINTS DE AUTH */

export const LOGIN_ENDPOINT = `${API_BASE_URL}auth/login`;

export const REGISTER_ENDPOINT = `${API_BASE_URL}auth/register`;

export const CONFIRM_EMAIL_ENDPOINT = `${API_BASE_URL}auth/email-confirmation`;

/* ENDPOINTS DE MAP-LOADER  */

export const MAP_ENDPOINT = `${API_BASE_URL}parking/level`
