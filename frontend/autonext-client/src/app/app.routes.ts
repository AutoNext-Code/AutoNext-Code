import { Routes } from '@angular/router';
import { authRoutes } from './auth/auth.routes';
import { LoginComponent } from './auth/pages/login/login.component';

export const routes: Routes = [
  {
    path: '',
    // component: HomeComponent
  },
  ...authRoutes,
  {
    path: '**',
    redirectTo: ''
  }
];
