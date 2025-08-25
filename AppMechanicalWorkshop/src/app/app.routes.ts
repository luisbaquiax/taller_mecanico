import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { NavAdmin } from './components/admin/nav-admin/nav-admin';

export const routes: Routes = [
    {
        path: '',
        component: Login,
        title: 'Login'
    },
    {
      path: 'admin-nav',
      component: NavAdmin,
      title: 'Admin Navigation'
    }
];
