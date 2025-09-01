import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../services/auth.service';

interface ActiveJob {
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
  clienteNombre: string;
}

@Component({
  selector: 'app-employee-active-jobs',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatChipsModule
  ],
  templateUrl: './employee-active-jobs.component.html',
  styleUrls: ['./employee-active-jobs.component.css']
})
export class EmployeeActiveJobsComponent implements OnInit {

  activeJobs = signal<ActiveJob[]>([]);
  pendingJobs = signal<ActiveJob[]>([]);
  inProgressJobs = signal<ActiveJob[]>([]);
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
    this.loadActiveJobs();
  }

  loadEmployeeId() {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser?.userId) {
      this.empleadoId.set(currentUser.userId);
    } else {
      const userId = localStorage.getItem('userId');
      if (userId) {
        this.empleadoId.set(parseInt(userId));
      }
    }
  }

  loadActiveJobs() {
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
          const allJobs = response.trabajos || [];
          const pendingOnly = allJobs.filter((job: any) =>
            job.estadoTrabajo === 'Pendiente'
          );
          const inProgressOnly = allJobs.filter((job: any) =>
            job.estadoTrabajo === 'En progreso'
          );
          const activeOnly = [...pendingOnly, ...inProgressOnly];
          this.pendingJobs.set(pendingOnly);
          this.inProgressJobs.set(inProgressOnly);
          this.activeJobs.set(activeOnly);
        } else {
          this.error.set('Error al cargar los trabajos activos');
        }
        this.isLoading.set(false);
      },
      error: (error) => {
        this.error.set('Error al conectar con el servidor: ' + error.message);
        this.isLoading.set(false);
      }
    });
  }

  iniciarTrabajo(job: ActiveJob) {
    const empId = this.empleadoId();
    if (!empId) return;

    const requestBody = {
      empleadoId: empId,
      observaciones: 'Trabajo iniciado por el empleado'
    };

    this.http.put<any>(`http://localhost:8081/employee/job/${job.trabajoId}/iniciar`, requestBody).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.loadActiveJobs(); // Recargar la lista
        } else {
          this.error.set('Error al iniciar el trabajo');
        }
      },
      error: (error) => {
        this.error.set('Error al iniciar trabajo: ' + error.message);
      }
    });
  }

  finalizarTrabajo(job: ActiveJob) {
    const empId = this.empleadoId();
    if (!empId) return;

    const requestBody = {
      empleadoId: empId,
      observaciones: 'Trabajo completado por el empleado'
    };

    this.http.put<any>(`http://localhost:8081/employee/job/${job.trabajoId}/finalizar`, requestBody).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.loadActiveJobs(); // Recargar la lista
        } else {
          this.error.set('Error al finalizar el trabajo');
        }
      },
      error: (error) => {
        this.error.set('Error al finalizar trabajo: ' + error.message);
      }
    });
  }

  verDetalles(job: ActiveJob) {
    this.router.navigate(['/employee/job-details', job.trabajoId]);
  }

  refreshActiveJobs() {
    this.loadActiveJobs();
  }

  goBack() {
    this.router.navigate(['/employee/jobs']);
  }

  getTiempoTranscurrido(fechaInicio: string): string {
    if (!fechaInicio) return 'No iniciado';

    const inicio = new Date(fechaInicio);
    const ahora = new Date();
    const diff = ahora.getTime() - inicio.getTime();

    const horas = Math.floor(diff / (1000 * 60 * 60));
    const minutos = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));

    if (horas > 0) {
      return `${horas}h ${minutos}m`;
    } else {
      return `${minutos}m`;
    }
  }

  getChipColor(estado: string): string {
    switch (estado) {
      case 'Pendiente':
        return 'primary';
      case 'En progreso':
        return 'accent';
      default:
        return 'primary';
    }
  }

  getChipIcon(estado: string): string {
    switch (estado) {
      case 'Pendiente':
        return 'schedule';
      case 'En progreso':
        return 'work';
      default:
        return 'schedule';
    }
  }
}
