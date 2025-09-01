import { Component, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Login } from './components/login/login';
import { NavAdmin } from './components/admin/nav-admin/nav-admin';
import { RegisterClientComponent } from './components/admin/register-client/register-client';
import { RegisterUser } from './components/admin/register-user/register-user';
import { ListUsersComponent } from './components/admin/list-users/list-users';
import { RegisterVehicleComponent } from './components/admin/register-vehicle/register-vehicle';
import { NavUsers } from './components/users/nav-users/nav-users';
import { MainUsers } from './components/users/main-users/main-users';
import { combineLatest } from 'rxjs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
  {
    path: 'client',
    component: NavUsers,
    children: [{
      path: '',
      component: MainUsers,
      title: 'Main view',
    }
    ],
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes), BrowserAnimationsModule],
  exports: [RouterModule]
})

export class AppRoutingModule { }


