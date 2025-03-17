import { Routes } from '@angular/router';

export const routes: Routes = [
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
        redirectTo: 'home'
    }
];