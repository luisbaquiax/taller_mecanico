import { Component, NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { Login } from './components/login/login';
import { NavAdminComponent } from './components/admin/nav-admin/nav-admin';
import { RegisterClientComponent } from './components/admin/register-client/register-client';
import { RegisterUser } from './components/admin/register-user/register-user';
import { ListUsersComponent } from './components/admin/list-users/list-users';
import { RegisterVehicleComponent } from './components/admin/register-vehicle/register-vehicle';
import { ListServicesComponent } from './components/admin/list-services/list-services';
import { ListJobsComponent } from './components/admin/list-jobs/list-jobs';
import { DetailJobComponent } from './components/admin/list-jobs/detail-jobs/detail-job';
import { AssigmentsJobComponent } from './components/admin/list-jobs/detail-jobs/assigments-job/assigments-job';
import { InvoicesJobComponent } from './components/admin/list-jobs/detail-jobs/invoices-job/invoices-job';
import { ServicesJobComponent } from './components/admin/list-jobs/detail-jobs/services-job/services-job';
import { PartsJobComponent } from './components/admin/list-jobs/detail-jobs/parts-job/parts-job';
import { UpdateJobsComponent } from './components/admin/list-jobs/detail-jobs/update-jobs/update-jobs';
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
    path: 'user/profile',
    component: UserProfileComponent,
    title: 'Mi Perfil',
  },
  {
    path: 'admin',
    component: NavAdminComponent,
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
        path: 'list-services',
        component: ListServicesComponent,
        title: 'List Services',
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
      {
        path: 'list-jobs',
        component: ListJobsComponent,
        title: 'List Jobs',
      },
      {
        path: 'detail-job/:id',
        component: DetailJobComponent,
        title: 'Detail Job',
        children: [
          {
            path: 'assigments-job',
            component: AssigmentsJobComponent,
            title: 'Assigments Job',
          },
          {
            path: 'invoices-job',
            component: InvoicesJobComponent,
            title: 'Invoices Job',
          },
          {
            path: 'services-job',
            component: ServicesJobComponent,
            title: 'Services Job',
          },
          {
            path: 'parts-job',
            component: PartsJobComponent,
            title: 'Parts Job',
          },
          {
            path: 'update-job',
            component: UpdateJobsComponent,
            title: 'Update Job',
          },
        ],
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
        pathMatch: 'full',
      },
      {
        path: 'dashboard',
        component: EmployeeDashboardComponent,
        title: 'Employee Dashboard',
      },
      {
        path: 'jobs',
        component: EmployeeJobsComponent,
        title: 'Trabajos Asignados',
      },
      {
        path: 'active-jobs',
        component: EmployeeActiveJobsComponent,
        title: 'Trabajos Activos',
      },
      {
        path: 'history',
        component: EmployeeHistoryComponent,
        title: 'Historial de Trabajos',
      },
      { path: 'job-details/:id', component: JobDetailsComponent, title: 'Detalles Trabajos' },
      {
        path: 'profile',
        redirectTo: '/user/profile',
        pathMatch: 'full',
      },
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
    children: [
      {
        path: '',
        component: MainUsers,
        title: 'Main view',
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes), BrowserAnimationsModule],
  exports: [RouterModule]
})

export class AppRoutingModule { }


