import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatChipsModule } from '@angular/material/chips';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../services/auth.service';
import {FormsModule} from '@angular/forms';

interface CompletedJob {
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
  selector: 'app-employee-history',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatChipsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    FormsModule
  ],
  templateUrl: './employee-history.component.html',
  styleUrls: ['./employee-history.component.css']
})
export class EmployeeHistoryComponent implements OnInit {

  completedJobs = signal<CompletedJob[]>([]);
  allCompletedJobs = signal<CompletedJob[]>([]);
  isLoading = signal(false);
  error = signal<string | null>(null);
  empleadoId = signal<number | null>(null);

  filtroTipo = signal<string>('');
  filtroFecha = signal<string>('');

  constructor(
    private router: Router,
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.loadEmployeeId();
    this.loadCompletedJobs();
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

  loadCompletedJobs() {
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
          // Filtrar solo trabajos completados
          const allJobs = response.trabajos || [];
          const completedOnly = allJobs.filter((job: any) =>
            job.estadoTrabajo === 'Completado'
          );
          this.allCompletedJobs.set(completedOnly);
          this.completedJobs.set(completedOnly);
        } else {
          this.error.set('Error al cargar el historial de trabajos');
        }
        this.isLoading.set(false);
      },
      error: (error) => {
        this.error.set('Error al conectar con el servidor: ' + error.message);
        this.isLoading.set(false);
      }
    });
  }

  applyFilters() {
    let filtered = [...this.allCompletedJobs()];

    // Filtro por tipo de mantenimiento
    if (this.filtroTipo()) {
      filtered = filtered.filter(job =>
        job.tipoMantenimiento.toLowerCase().includes(this.filtroTipo().toLowerCase())
      );
    }

    if (this.filtroFecha()) {
      const now = new Date();
      const daysAgo = parseInt(this.filtroFecha());
      const cutoffDate = new Date(now.getTime() - (daysAgo * 24 * 60 * 60 * 1000));

      filtered = filtered.filter(job => {
        if (!job.fechaFinalizacion) return false;
        const jobDate = new Date(job.fechaFinalizacion);
        return jobDate >= cutoffDate;
      });
    }

    this.completedJobs.set(filtered);
  }

  clearFilters() {
    this.filtroTipo.set('');
    this.filtroFecha.set('');
    this.completedJobs.set([...this.allCompletedJobs()]);
  }

  verDetalles(job: CompletedJob) {
    this.router.navigate(['/employee/job-details', job.trabajoId]);
    console.log('Ver detalles del trabajo:', job.trabajoId);
  }

  refreshHistory() {
    this.loadCompletedJobs();
  }

  goBack() {
    this.router.navigate(['/employee/jobs']);
  }

  getTiempoDuracion(fechaInicio?: string, fechaFin?: string): string {
    if (!fechaInicio || !fechaFin) return 'N/A';

    const inicio = new Date(fechaInicio);
    const fin = new Date(fechaFin);
    const diff = fin.getTime() - inicio.getTime();

    const horas = Math.floor(diff / (1000 * 60 * 60));
    const minutos = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));

    if (horas > 0) {
      return `${horas}h ${minutos}m`;
    } else {
      return `${minutos}m`;
    }
  }

  getTiempoDesdeCompletion(fechaFin?: string): string {
    if (!fechaFin) return 'N/A';

    const fin = new Date(fechaFin);
    const ahora = new Date();
    const diff = ahora.getTime() - fin.getTime();

    const dias = Math.floor(diff / (1000 * 60 * 60 * 24));
    const horas = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));

    if (dias > 0) {
      return `Hace ${dias} día${dias > 1 ? 's' : ''}`;
    } else if (horas > 0) {
      return `Hace ${horas} hora${horas > 1 ? 's' : ''}`;
    } else {
      return 'Hace menos de 1 hora';
    }
  }

  get totalHorasReales(): number {
    return this.completedJobs().reduce((total, job) => {
      if (job.fechaInicio && job.fechaFinalizacion) {
        const inicio = new Date(job.fechaInicio);
        const fin = new Date(job.fechaFinalizacion);
        const diff = fin.getTime() - inicio.getTime();
        const horas = diff / (1000 * 60 * 60);
        return total + horas;
      }
      return total;
    }, 0);
  }

  get totalHorasEstimadas(): number {
    return this.completedJobs().reduce((total, job) => {
      return total + (job.horasEstimadas || 0);
    }, 0);
  }

  get eficienciaPromedio(): number {
    const horasReales = this.totalHorasReales;
    const horasEstimadas = this.totalHorasEstimadas;

    if (horasEstimadas === 0) return 0;
    return (horasEstimadas / horasReales) * 100;
  }
}
