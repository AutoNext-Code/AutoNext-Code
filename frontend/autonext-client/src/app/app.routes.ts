import { Routes } from '@angular/router';
import { MapsComponent } from './maps/maps/maps.component';
import { AuthLayoutComponent } from './auth/layouts/auth-layout/auth-layout.component';
import { LoginComponent } from './auth/pages/login/login.component';

export const routes: Routes = [
  {
    path: '',
    component: MapsComponent,
  },
  {
    path: 'auth',
    redirectTo: 'auth/login',
    pathMatch: 'full',
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent,
      },
    ]
  },
  {
    path: '**',
    redirectTo: '',
  }
];
