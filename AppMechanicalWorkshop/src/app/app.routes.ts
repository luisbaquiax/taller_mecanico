import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Login } from './components/login/login';
import { NavAdmin } from './components/admin/nav-admin/nav-admin';
import { RegisterClientComponent } from './components/admin/register-client/register-client';
import { RegisterUser } from './components/admin/register-user/register-user';
import { ListUsersComponent } from './components/admin/list-users/list-users';
import { RegisterVehicleComponent } from './components/admin/register-vehicle/register-vehicle';
import {NavEmployeeComponent} from './components/employee/nav-employee/nav-employee.component';
import {EmployeeDashboardComponent} from './components/employee/employee-dashboard/employee-dashboard.component';
import {EmployeeJobsComponent} from './components/employee/employee-jobs/employee-jobs.component';
import {EmployeeActiveJobsComponent} from './components/employee/employee-active/employee-actvie.jobs.component';
import {EmployeeHistoryComponent} from './components/employee/employee-history/employee-history.component';
import {JobDetailsComponent} from './components/job/job-details.component';
import {UserProfileComponent} from './components/user/user-profile.component';
import { RegisterPartComponenet } from './components/admin/register-part/register-part';
import { AddInventoryComponent } from './components/admin/add-inventory/add-inventory';
import { ListPartsComponent } from './components/admin/list-parts/list-parts';

export const routes: Routes = [
  {
    path: '',
    component: Login,
    title: 'Login',
  },
  {
    path: 'user/profile',
    component: UserProfileComponent,
    title: 'Mi Perfil'
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
      },
      {
        path: 'list-parts',
        component: ListPartsComponent,
        title: 'List Parts',
      },
      {
        path: 'register-part',
        component: RegisterPartComponenet,
        title: 'Register Part',
      },
      {
        path: 'add-inventory',
        component: AddInventoryComponent,
        title: 'Add Inventory',
      },
    ],
  },
  {
    path: 'employee',
    component: NavEmployeeComponent,
    children: [
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      {
        path: 'dashboard',
        component: EmployeeDashboardComponent,
        title: 'Employee Dashboard',
      },
      {
        path: 'jobs',
        component: EmployeeJobsComponent,
        title: 'Trabajos Asignados'
      },
      {
        path:'active-jobs',
        component: EmployeeActiveJobsComponent,
        title: 'Trabajos Activos'
      },
      {
        path: 'history',
        component: EmployeeHistoryComponent,
        title: 'Historial de Trabajos'
      },
      { path: 'job-details/:id',
        component: JobDetailsComponent,
        title: 'Detalles Trabajos'
      },
      {
        path: 'profile',
        redirectTo: '/user/profile',
        pathMatch: 'full'
      },
    ],
  },
  {
    path: 'register-client',
    component: RegisterClientComponent,
    title: 'Register Client',
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }


