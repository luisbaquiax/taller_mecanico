import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';
// Importaciones de Angular Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatOption } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatMenu } from '@angular/material/menu';
import { MatDialog } from '@angular/material/dialog';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { DatePipe } from '@angular/common';
import { MatListModule } from '@angular/material/list';

// Importaciones locales
import { JobDTO } from '../../../interfaces/JobDTO';
import { JobService } from '../../../services/job-service/Job.service';
import { StatusJobsService } from '../../../services/job-service/StatusJobs.service';
import { TypeJobService } from '../../../services/type-service/TypeJob.service';
import { TypeJobs } from '../../../interfaces/TypeJobs';
import { StatusJobsDTO } from '../../../interfaces/StatusJobsDTO';

import {
  MatSnackBar,
  MatSnackBarAction,
  MatSnackBarActions,
  MatSnackBarLabel,
  MatSnackBarRef,
} from '@angular/material/snack-bar';
// Importaciones de formularios reactivos
import { FormGroup, Validators, ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { VehicleDTO } from '../../../interfaces/VehicleDTO';
import { VehicleService } from '../../../services/vehicle-service/Vehicle.service';
import { UserDTO } from '../../../interfaces/UserDTO';
import { M } from '@angular/cdk/keycodes';
import { StatusJobsEnum } from '../../../enums/StatusJobsEnum';
import { JobAssigmentService } from '../../../services/job-service/JobAssigment.service';
import { JobAssigmentDTO } from '../../../interfaces/JobAssigmentDTO';
import { UserService } from '../../../services/user-service/User.service';
import { JobPartsDTO } from '../../../interfaces/JobPartsDTO';
import { JobServicesService } from '../../../services/job-service/JobServices.service';
import { JobPartsService } from '../../../services/job-service/JobParts.service';
import { JobServicesDTO } from '../../../interfaces/JobServicesDTO';
import { ServiceTypesService } from '../../../services/job-service/ServiceTypes.service';
import { ServiceTypesDTO } from '../../../interfaces/ServiceTypesDTO';
import { PartDTO } from '../../../interfaces/PartDTO';
import { PartService } from '../../../services/Part.service';

@Component({
  selector: 'app-list-jobs',
  imports: [
    MatTableModule,
    MatMenu,
    MatMenuModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    ReactiveFormsModule,
    CommonModule,
    RouterModule,
    MatDividerModule,
    MatListModule,
  ],
  templateUrl: './list-jobs.html',
  styleUrl: './list-jobs.css',
})
export class ListJobsComponent implements OnInit {
  displayedColumns: string[] = [
    'jobId',
    'createdBy',
    'vehicleId',
    'typeJobId',
    'statusJobId',
    'description',
    'estimatedHours',
    'actions',
  ];
  dataSource: JobDTO[] = [];
  typeJobs: TypeJobs[] = [];
  statusJobs: StatusJobsDTO[] = [];
  vehicles: VehicleDTO[] = [];
  users: UserDTO[] = [];
  username: string = 'admin';
  jobView: JobDTO = {
    jobId: 0,
    createdBy: 0,
    vehicleId: 0,
    startedAt: '',
    finishedAt: '',
    typeJobId: 0,
    statusJobId: 0,
    description: '',
    estimatedHours: 0,
  };
  assigments: JobAssigmentDTO[] = [];
  servicesApplied: string[] = [];
  jobParts: JobPartsDTO[] = [];
  jobServices: JobServicesDTO[] = [];
  showJobView: boolean = false;
  serviceTypes: ServiceTypesDTO[] = [];
  parts: PartDTO[] = [];

  durationSecond: number = 5000;

  registerForm!: FormGroup;

  constructor(
    private _snack: MatSnackBar,
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private jobService: JobService,
    private jobServcesService: JobServicesService,
    private jobPartsService: JobPartsService,
    private statusJobService: StatusJobsService,
    private typeJobService: TypeJobService,
    private vehicleService: VehicleService,
    private jobAssigmentService: JobAssigmentService,
    private serviceTypeService: ServiceTypesService,
    private partService: PartService
  ) {
    this.registerForm = this.fb.group({
      statusJob: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.setInitialValues();
  }

  setInitialValues(): void {
    this.setDataSource();
    this.setTypeJobsByType();
    this.setStatusJobs();
    this.setVehicles();
    this.setUsers();
    this.setServices();
    this.setParts();
  }
  setDataSource(): void {
    this.jobService.getJobs().subscribe((data: JobDTO[]) => {
      this.dataSource = data;
    });
  }

  setParts(){
    this.partService.getParts().subscribe((data: PartDTO[]) => {
      this.parts = data;
    });
  }

  setServices(): void {
    this.serviceTypeService.getAll().subscribe((data: ServiceTypesDTO[]) => {
      this.serviceTypes = data;
    });
  }

  setStatusJobs(): void {
    this.statusJobService.getAllStatusJobs().subscribe((data: StatusJobsDTO[]) => {
      this.statusJobs = data;
    });
  }

  setStatusJobsByStatus(statusId: number): void {
    this.jobService.getJobsByStatusId(statusId).subscribe((data: JobDTO[]) => {
      this.dataSource = data;
    });
  }

  setTypeJobsByType(): void {
    this.typeJobService.getAllTypeJobs().subscribe((data: TypeJobs[]) => {
      this.typeJobs = data;
    });
  }

  setVehicles(): void {
    this.vehicleService.getAllVehicles().subscribe((data: VehicleDTO[]) => {
      this.vehicles = data;
    });
  }

  setUsers(): void {
    this.userService.getAllUsers().subscribe((data: UserDTO[]) => {
      this.users = data;
    });
  }

  getJobStatusName(statusId: number): string {
    const status = this.statusJobs.find((s) => s.statusJobId === statusId);
    return status ? status.nameStatusJob : 'Desconocido';
  }

  getJobTypeName(typeId: number): string {
    const type = this.typeJobs.find((t) => t.typeJobId === typeId);
    return type ? type.nameTypeJob : 'Desconocido';
  }

  getLicencePlate(vehicleId: number): string {
    const vehicle = this.vehicles.find((v) => v.vehicleId === vehicleId);
    return vehicle ? vehicle.licencePlate : 'Desconocido';
  }

  getCreatedBy(userId: number): string {
    return this.username;
  }

  updateJob(job: JobDTO): void {
    this.jobService.updateJob(job).subscribe((data: JobDTO) => {
      this.setDataSource();
    });
  }

  cancelJob(job: JobDTO): void {
    if (confirm('¿Desea cancelar el trabajo?')) {
      job.statusJobId = StatusJobsEnum.getId(StatusJobsEnum.CANCELADO);
      this.jobService.updateJob(job).subscribe({
        next: (data: JobDTO) => {
          this._snack.open('El trabajo ha sido cancelado', 'Cerrar', {
            duration: this.durationSecond,
          });
          this.setDataSource();
        },
        error: (error) => {
          throw error;
        },
      });
    }
  }

  viewJob(job: JobDTO) {
    //this.router.navigate(['admin/detail-job', job.jobId]);
    this.jobView = job;
    this.showJobView = true;
    //Assigments
    this.jobAssigmentService
      .getJobAssigmentsByJob(job.jobId)
      .subscribe((data: JobAssigmentDTO[]) => {
        this.assigments = data;
      });
    //Services
    this.jobServcesService.getJobServicesByJob(job.jobId).subscribe((data: JobServicesDTO[]) => {
      this.jobServices = data;
    });
    //Parts
    this.jobPartsService.getPartsByJob(job.jobId).subscribe((data: JobPartsDTO[]) => {
      this.jobParts = data;
    });
  }

  closeShwoView() {
    this.showJobView = false;
  }

  searchUser(useId: number): UserDTO {
    return this.users.find((user) => user.userId === useId)!;
  }

  searchService(serviceId: number): ServiceTypesDTO {
    return this.serviceTypes.find((service) => service.serviceTypeId === serviceId)!;
  }

  searchPart(partId: number){
    return this.parts.find((part) => part.partId === partId)!;
  }

  onSubmit(): void {
    const statusJobId = this.registerForm.get('statusJob')?.value;
    this.setStatusJobsByStatus(statusJobId);
  }
}
