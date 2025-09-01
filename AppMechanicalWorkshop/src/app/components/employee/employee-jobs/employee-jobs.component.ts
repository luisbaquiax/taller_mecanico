import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatBadgeModule } from '@angular/material/badge';
import { MatChipsModule } from '@angular/material/chips';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../services/auth.service';

interface Job {
  trabajoId: number;
  vehiculoId: number;
  placaVehiculo: string;
  marcaVehiculo: string;
  modeloVehiculo: string;
  tipoMantenimiento: string;
  estadoTrabajo: string;
  descripcion: string;
  horasEstimadas: number;
  fechaAsignacion: string;
  fechaInicio?: string;
  fechaFinalizacion?: string;
  clienteNombre: string;
}

@Component({
  selector: 'app-employee-jobs',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatBadgeModule,
    MatChipsModule
  ],
  templateUrl: './employee-jobs.component.html',
  styleUrls: ['./employee-jobs.component.css']
})
export class EmployeeJobsComponent implements OnInit {

  jobs = signal<Job[]>([]);
  isLoading = signal(false);
  error = signal<string | null>(null);
  empleadoId = signal<number | null>(null);

  constructor(
    private router: Router,
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.loadEmployeeId();
    this.loadJobs();
  }

  loadEmployeeId() {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser?.userId) {  // Usar userId en lugar de id
      this.empleadoId.set(currentUser.userId);
    } else {
      const userId = localStorage.getItem('userId');
      if (userId) {
        this.empleadoId.set(parseInt(userId));
      }
    }
  }

  loadJobs() {
    const empId = this.empleadoId();
    if (!empId) {
      this.error.set('No se pudo obtener el ID del empleado');
      return;
    }

    this.isLoading.set(true);
    this.error.set(null);

    this.http.get<any>(`http://localhost:8081/employee/jobs/${empId}`).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.jobs.set(response.trabajos || []);
        } else {
          this.error.set('Error al cargar los trabajos');
        }
        this.isLoading.set(false);
      },
      error: (error) => {
        this.error.set('Error al conectar con el servidor: ' + error.message);
        this.isLoading.set(false);
      }
    });
  }

  iniciarTrabajo(job: Job) {
    const empId = this.empleadoId();
    if (!empId) return;

    const requestBody = {
      empleadoId: empId
    };

    this.http.post<any>(`http://localhost:8081/employee/job/${job.trabajoId}/iniciar`, requestBody).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.loadJobs(); // Recargar la lista
        } else {
          this.error.set('Error al iniciar el trabajo');
        }
      },
      error: (error) => {
        this.error.set('Error al iniciar trabajo: ' + error.message);
      }
    });
  }

  finalizarTrabajo(job: Job) {
    const empId = this.empleadoId();
    if (!empId) return;

    const requestBody = {
      empleadoId: empId,
      observaciones: 'Trabajo completado por el empleado'
    };

    this.http.put<any>(`http://localhost:8081/employee/job/${job.trabajoId}/finalizar`, requestBody).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.loadJobs(); // Recargar la lista
        } else {
          this.error.set('Error al finalizar el trabajo');
        }
      },
      error: (error) => {
        this.error.set('Error al finalizar trabajo: ' + error.message);
      }
    });
  }

  verDetalles(job: Job) {
    this.router.navigate(['/employee/job-details', job.trabajoId]);
    console.log('Ver detalles del trabajo:', job.trabajoId);
  }

  getEstadoColor(estado: string): string {
    switch (estado.toUpperCase()) {
      case 'PENDIENTE':
        return 'warn';
      case 'EN PROGRESO':
        return 'accent';
      case 'COMPLETADO':
        return 'primary';
      default:
        return '';
    }
  }

  getEstadoIcon(estado: string): string {
    switch (estado.toUpperCase()) {
      case 'PENDIENTE':
        return 'assignment';
      case 'EN PROGRESO':
        return 'work';
      case 'COMPLETADO':
        return 'check_circle';
      default:
        return 'help';
    }
  }

  refreshJobs() {
    this.loadJobs();
  }

  get trabajosActivos() {
    return this.jobs().filter(job => job.estadoTrabajo === 'En progreso').length;
  }

  get trabajosPendientes() {
    return this.jobs().filter(job => job.estadoTrabajo === 'Pendiente').length;
  }

  get trabajosCompletados() {
    return this.jobs().filter(job => job.estadoTrabajo === 'Completado').length;
  }
}
