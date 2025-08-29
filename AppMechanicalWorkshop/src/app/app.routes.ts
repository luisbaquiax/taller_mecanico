import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Login } from './components/login/login';
import { NavAdmin } from './components/admin/nav-admin/nav-admin';
import { RegisterClientComponent } from './components/admin/register-client/register-client';
import { RegisterUser } from './components/admin/register-user/register-user';
import { ListUsersComponent } from './components/admin/list-users/list-users';
import { RegisterVehicleComponent } from './components/admin/register-vehicle/register-vehicle';

export const routes: Routes = [
  {
    path: '',
    component: Login,
    title: 'Login',
  },
  {
    path: 'admin',
    component: NavAdmin,
    children: [
      {
        path: 'register-user',
        component: RegisterUser,
        title: 'Register User',
      },
      {
        path: 'list-users',
        component: ListUsersComponent,
        title: 'List Users',
      },
      {
        path: 'register-vehicle',
        component: RegisterVehicleComponent,
        title: 'Register Vehicle',
      }
    ],
  },
  {
    path: 'register-client',
    component: RegisterClientComponent,
    title: 'Register Client',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }


