import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './auth/layouts/auth-layout/auth-layout.component';
import { LoginComponent } from './auth/pages/login/login.component';

export const routes: Routes = [
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
        path: 'home',
        loadComponent: () => import('./user/pages/home-user-page/home-user-page.component').then(m => m.HomeUserPageComponent)
    },
    {
        path: 'admin-home',
        loadComponent: () => import('./admin/pages/home-admin-page/home-admin-page.component').then(m => m.HomeAdminPageComponent)
    },
    {
          path: '**',
          redirectTo: 'home',
          pathMatch: 'full'
    }
];