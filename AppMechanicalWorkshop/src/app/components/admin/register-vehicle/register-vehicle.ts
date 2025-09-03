import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatFormField } from '@angular/material/form-field';
import { MatLabel } from '@angular/material/form-field';
import { MatError } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';

// Importaciones de Angular Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatOption } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select';
// Importaciones de formularios reactivos
import { FormGroup, Validators, ReactiveFormsModule, FormBuilder } from '@angular/forms';
import {
  MatSnackBar,
  MatSnackBarAction,
  MatSnackBarActions,
  MatSnackBarLabel,
  MatSnackBarRef,
} from '@angular/material/snack-bar';
import { F, V } from '@angular/cdk/keycodes';
import { UserService } from '../../../services/user-service/User.service';
import { UserDTO } from '../../../interfaces/UserDTO';
import { Roles } from '../../../enums/Roles';
import { VehicleDTO } from '../../../interfaces/VehicleDTO';
import { ClientDTO } from '../../../interfaces/ClientDTO';
import { ClientService } from '../../../services/user-service/Client.service';
import { VehicleService } from '../../../services/vehicle-service/Vehicle.service';
import { TypeJobs } from '../../../interfaces/TypeJobs';
import { TypeJobService } from '../../../services/type-service/TypeJob.service';
import { JobDTO } from '../../../interfaces/JobDTO';
import { JobService } from '../../../services/job-service/Job.service';
import { ServiceTypesDTO } from '../../../interfaces/ServiceTypesDTO';
import { ServiceTypesService } from '../../../services/job-service/ServiceTypes.service';
import { ServiceType } from '../../../interfaces/ServiceType';
// Importaciones locales

@Component({
  selector: 'app-register-vehicle',
  imports: [
    CommonModule,
    RouterModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    ReactiveFormsModule,
  ],
  templateUrl: './register-vehicle.html',
  styleUrl: './register-vehicle.css',
})
export class RegisterVehicleComponent implements OnInit {
  vehicleForm: FormGroup;
  vehicles: VehicleDTO[] = [];
  clients: ClientDTO[] = [];
  typeJobs: TypeJobs[] = [];
  typeJobSelected: string = '';
  servicesTypies: ServiceTypesDTO[] = [];
  servicesSelected: number[] = [];

  newJob: JobDTO = {
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

  constructor(
    private fb: FormBuilder,
    private _snackBar: MatSnackBar,
    private vehicleService: VehicleService,
    private clientService: ClientService,
    private typeJobService: TypeJobService,
    private jobService: JobService,
    private serviceTypesService: ServiceTypesService
  ) {
    this.vehicleForm = this.fb.group({
      clientId: ['', Validators.required],
      vehicleId: ['', Validators.required],
      licencePlate: ['', Validators.required],
      brand: ['', Validators.required],
      model: ['', Validators.required],
      year: [
        '',
        [Validators.required, Validators.min(1900), Validators.max(new Date().getFullYear())],
      ],
      color: ['', Validators.required],
      typeJob: ['', Validators.required],
      descriptionProblem: ['', Validators.required],
      services: [''],
    });
  }

  ngOnInit(): void {
    this.setVehicles();
    this.setClients();
    this.setTypeJobs();
    this.setServiceTypes();
  }

  setVehicles(): void {
    this.vehicleService.getAllVehicles().subscribe((data: VehicleDTO[]) => {
      this.vehicles = data;
    });
  }

  setClients(): void {
    this.clientService.getAllClients().subscribe((data: ClientDTO[]) => {
      this.clients = data;
    });
  }

  setTypeJobs(): void {
    this.typeJobService.getAllTypeJobs().subscribe((data: TypeJobs[]) => {
      this.typeJobs = data;
    });
  }

  setServiceTypes(): void {
    this.serviceTypesService.getAll().subscribe((data: ServiceTypesDTO[]) => {
      this.servicesTypies = data;
      console.log('tipos de servicios', this.servicesTypies);
    });
  }

  searchClientByNit(nitClient: string): void {
    const client = this.clients.find((client) => client.nit === nitClient);
  }

  searchVehicle() {
    const vehicle = this.vehicles.find(
      (vehicle) => vehicle.licencePlate === this.vehicleForm.get('licencePlate')?.value
    );

    if (vehicle) {
      this.vehicleForm.get('clientId')?.setValue(vehicle.clientId);
      this.vehicleForm.get('vehicleId')?.setValue(vehicle.vehicleId);
      this.vehicleForm.get('licencePlate')?.setValue(vehicle.licencePlate);
      this.vehicleForm.get('brand')?.setValue(vehicle.brand);
      this.vehicleForm.get('model')?.setValue(vehicle.model);
      this.vehicleForm.get('year')?.setValue(vehicle.year);
      this.vehicleForm.get('color')?.setValue(vehicle.color);
    } else {
      this.vehicleForm.get('vehicleId')?.setValue(0);
    }
  }

  setTypeJobSelected(): void {
    const typeJob = this.typeJobs.find(
      (typeJob) => typeJob.typeJobId === this.vehicleForm.get('typeJob')?.value
    );
    if (typeJob) {
      this.typeJobSelected = typeJob.descriptionTypeJob;
    }
  }

  onSubmit() {
    if (this.vehicleForm.valid) {
      this.servicesSelected = this.vehicleForm.get('services')?.value || [];
      if (this.servicesSelected.length > 0) {
        const vehicle: VehicleDTO = {
          vehicleId: 0,
          clientId: this.vehicleForm.get('clientId')?.value,
          licencePlate: this.vehicleForm.get('licencePlate')?.value,
          brand: this.vehicleForm.get('brand')?.value,
          model: this.vehicleForm.get('model')?.value,
          year: this.vehicleForm.get('year')?.value,
          color: this.vehicleForm.get('color')?.value,
        };
        console.log('servicios seleccionados', this.servicesSelected);

        try {
          this.vehicleService.saveVehicle(vehicle).subscribe({
            next: (data: VehicleDTO) => {
              this.newJob.vehicleId = data.vehicleId;
              this.newJob.createdBy = 1;
              this.newJob.typeJobId = this.vehicleForm.get('typeJob')?.value;
              this.newJob.description = this.vehicleForm.get('descriptionProblem')?.value;

              // Ahora registramos el trabajo
              this.registerJob(this.newJob, this.servicesSelected);

              this._snackBar.open('Vehículo registrado correctamente', 'Cerrar', {
                duration: 5000,
              });

              this.resetValues();
              this.setVehicles();
            },
            error: (error) => {
              console.error('Error al registrar vehículo:', error);
              this._snackBar.open(`Error al registrar el vehículo: ${error.message}`, 'Cerrar', {
                duration: 5000,
              });
            },
          });
        } catch (error) {
          console.error('Error inesperado:', error);
          this._snackBar.open('Error al registrar el vehículo', 'Cerrar', {
            duration: 5000,
          });
        }
      } else {
        this._snackBar.open('Por favor, seleccione al menos un servicio', 'Cerrar', {
          duration: 5000,
        });
      }
    } else {
      console.log('Valores del formulario:', this.vehicleForm.value);
      this._snackBar.open('Por favor, complete todos los campos', 'Cerrar', {
        duration: 5000,
      });
    }
  }

  registerJob(job: JobDTO, servicesSelected: number[]): void {
    this.jobService.saveJob(job, servicesSelected).subscribe({
      next: (data: JobDTO) => {},
      error: (error) => {
        throw error;
      },
    });
  }

  resetValues() {
    this.vehicleForm.reset();
    this.newJob = {
      jobId: 0,
      vehicleId: 0,
      typeJobId: 0,
      description: '',
      createdBy: 0,
      startedAt: '',
      finishedAt: '',
      statusJobId: 0,
      estimatedHours: 0,
    };
  }
}
