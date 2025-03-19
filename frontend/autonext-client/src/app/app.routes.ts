import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './auth/layouts/auth-layout/auth-layout.component';
import { LoginComponent } from './auth/pages/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';
import { RegisterComponent } from './auth/pages/register/register.component';
import { EmailConfirmationComponent } from './auth/pages/email-confirmation/email-confirmation.component';



export const routes: Routes = [
    {
      path: 'auth',
      component: AuthLayoutComponent,
      children: [
        {
          path: 'login',
          component: LoginComponent,
        },
        {
          path: 'register',
          component: RegisterComponent,
        },
        {
          path: 'email-confirmation/:token',
          component: EmailConfirmationComponent,
        }
      ]
    },
    {
        path: 'home',
        loadComponent: () => import('./user/pages/home-user-page/home-user-page.component').then(m => m.HomeUserPageComponent),
        /* canActivate: [AuthGuard] */
    },
    {
        path: 'admin-home',
        loadComponent: () => import('./admin/layout/admin-layout/home-admin-page.component').then(m => m.HomeAdminPageComponent),
        /* canActivate: [AuthGuard, RoleGuard], */
        children: [
          {
            path: 'panel-admin',
            loadComponent: () => import('./admin/pages/admin-panel-page/admin-panel-page.component').then(m => m.AdminPanelPageComponent),
          },
          {
            path: 'dashboard',
            loadComponent: () => import('./admin/pages/dashboard-page/dashboard-page.component').then(m => m.DashboardPageComponent),
          },
          {
            path: 'incidents',
            loadComponent: () => import('./admin/pages/incidents-page/incidents-page.component').then(m => m.IncidentsPageComponent),
          },
          {
            path: 'place-management',
            loadComponent: () => import('./admin/pages/place-management-page/place-management-page.component').then(m => m.PlaceManagementPageComponent),
          },
          {
            path: 'delegation-management',
            loadComponent: () => import('./admin/pages/delegation-management-page/delegation-management-page.component').then(m => m.DelegationManagementPageComponent),
          },
          {
            path: 'user-management',
            loadComponent: () => import('./admin/pages/user-management-page/user-management-page.component').then(m => m.UserManagementPageComponent),
          },
          {
            path: '',
            redirectTo: 'panel-admin',
            pathMatch: 'full'
        }
        ]
    },
    {
          path: '**',
          redirectTo: 'home',
          pathMatch: 'full'
    }
];
