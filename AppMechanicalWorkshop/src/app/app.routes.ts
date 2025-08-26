import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { NavAdmin } from './components/admin/nav-admin/nav-admin';
import { RegisterClientComponent } from './components/register-client/register-client';

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
    },
    {
      path: 'register-client',
      component: RegisterClientComponent,
      title: 'Register Client'
    }
];
