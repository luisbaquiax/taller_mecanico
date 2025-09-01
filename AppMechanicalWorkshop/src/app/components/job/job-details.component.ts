import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatStepperModule } from '@angular/material/stepper';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatChipsModule } from '@angular/material/chips';
import {MatTabChangeEvent, MatTabsModule} from '@angular/material/tabs';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {HttpClient, HttpParams} from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

interface JobDetail {
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

interface JobProgress {
  updateJobId: number;
  jobId: number;
  createdBy: number;
  statusUpdateJobId: number;
  notes: string;
  hoursSpent?: number;
  createdAt: string;
}

@Component({
  selector: 'app-job-details',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDialogModule,
    MatStepperModule,
    MatProgressBarModule,
    MatChipsModule,
    MatTabsModule,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './job-details.component.html',
  styleUrls: ['./job-details.component.css']
})
export class JobDetailsComponent implements OnInit {

  job = signal<JobDetail | null>(null);
  historial = signal<JobProgress[]>([]);
  isLoading = signal(true);
  error = signal<string | null>(null);
  empleadoId = signal<number | null>(null);
  trabajoId = signal<number | null>(null);

  // Formularios
  avanceForm = {
    descripcion: '',
    horasTrabajadas: null as number | null,
    tipo: 'AVANCE'
  };

  apoyoForm = {
    motivo: '',
    tipoEspecialista: 'ELECTRICISTA'
  };

  danosForm = {
    descripcionDanos: '',
    severidad: 'MEDIO'
  };

  repuestoForm = {
    repuestoId: null as number | null,
    cantidad: 1,
    observaciones: ''
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private authService: AuthService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadEmployeeId();
    this.route.params.subscribe(params => {
      this.trabajoId.set(+params['id']);
      this.loadJobDetails();
      this.loadJobHistory();        // <-- NUEVO
    });
  }

  refreshDetails() {
    this.loadJobDetails();
    this.loadJobHistory();          // <-- para refrescar también el historial
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

  isCompleted(): boolean {
    const j = this.job();
    if (!j) return false;
    return (j.estadoTrabajo ?? '').toUpperCase() === 'COMPLETADO' || !!j.fechaFinalizacion;
  }


  loadJobDetails() {
    const jobId = this.trabajoId();
    const empId = this.empleadoId();

    if (!jobId || !empId) {
      this.error.set('No se pudieron obtener los IDs necesarios');
      return;
    }

    this.isLoading.set(true);
    this.error.set(null);

    this.http.get<any>(`http://localhost:8081/employee/job/${jobId}/details/${empId}`).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.job.set(response.trabajo);
          this.loadJobHistory();
        } else {
          this.error.set('Error al cargar detalles del trabajo');
        }
        this.isLoading.set(false);
      },
      error: (error) => {
        this.error.set('Error al conectar con el servidor: ' + error.message);
        this.isLoading.set(false);
      }
    });
  }

  onTabChange(e: MatTabChangeEvent) {
    // dispara la carga cuando seleccionas la pestaña "Historial de Progreso"
    if (e.tab.textLabel === 'Historial de Progreso') {
      this.loadJobHistory();
    }
  }

  private loadJobHistory() {
    const jobId = this.trabajoId();
    const empId = this.empleadoId();
    if (!jobId || !empId) return;

    const params = new HttpParams().set('empleadoId', empId.toString());
    this.http
      .get<JobProgress[]>(`http://localhost:8081/employee/job/${jobId}/historial`, { params })
      .subscribe({
        next: rows => this.historial.set(rows ?? []),
        error: err => {
          this.historial.set([]);
          this.error.set('No se pudo cargar el historial');
        }
      });
  }

  // Acciones del trabajo
  iniciarTrabajo() {
    const jobId = this.trabajoId();
    const empId = this.empleadoId();
    if (!jobId || !empId) return;

    const requestBody = { empleadoId: empId };

    this.http.post<any>(`http://localhost:8081/employee/job/${jobId}/iniciar`, requestBody).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.loadJobDetails();
        }
      },
      error: (error) => {
        this.error.set('Error al iniciar trabajo: ' + error.message);
      }
    });
  }

  finalizarTrabajo() {
    const jobId = this.trabajoId();
    const empId = this.empleadoId();
    if (!jobId || !empId) return;

    const requestBody = {
      empleadoId: empId,
      observaciones: 'Trabajo completado'
    };

    this.http.put<any>(`http://localhost:8081/employee/job/${jobId}/finalizar`, requestBody).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.loadJobDetails();
        }
      },
      error: (error) => {
        this.error.set('Error al finalizar trabajo: ' + error.message);
      }
    });
  }

  // Registrar avance
  registrarAvance() {
    const jobId = this.trabajoId();
    const empId = this.empleadoId();
    if (!jobId || !empId || !this.avanceForm.descripcion.trim()) return;

    const requestBody = {
      empleadoId: empId,
      descripcion: this.avanceForm.descripcion,
      horasTrabajadas: this.avanceForm.horasTrabajadas,
      tipo: this.avanceForm.tipo
    };

    this.http.post<any>(`http://localhost:8081/employee/job/${jobId}/avance`, requestBody).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.snackBar.open('Avance registrado correctamente', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
          this.loadJobDetails();
          this.limpiarFormAvance();
        }
      },
      error: (error) => {
        this.error.set('Error al registrar avance: ' + error.message);
      }
    });
  }

  // Solicitar apoyo
  solicitarApoyo() {
    const jobId = this.trabajoId();
    const empId = this.empleadoId();
    if (!jobId || !empId || !this.apoyoForm.motivo.trim()) return;

    const requestBody = {
      empleadoId: empId,
      motivo: this.apoyoForm.motivo,
      tipoEspecialista: this.apoyoForm.tipoEspecialista
    };

    this.http.post<any>(`http://localhost:8081/employee/job/${jobId}/solicitar-apoyo`, requestBody).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.snackBar.open('Apoyo solicitado correctamente', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
          this.loadJobDetails();
          this.limpiarFormApoyo();
        }
      },
      error: (error) => {
        this.error.set('Error al solicitar apoyo: ' + error.message);
      }
    });
  }

  // Reportar daños
  reportarDanos() {
    const jobId = this.trabajoId();
    const empId = this.empleadoId();
    if (!jobId || !empId || !this.danosForm.descripcionDanos.trim()) return;

    const requestBody = {
      empleadoId: empId,
      descripcionDanos: this.danosForm.descripcionDanos,
      severidad: this.danosForm.severidad
    };

    this.http.post<any>(`http://localhost:8081/employee/job/${jobId}/reportar-danos`, requestBody).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.snackBar.open('Da;o reportado correctamente', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
          this.loadJobDetails();
          this.limpiarFormDanos();
        }
      },
      error: (error) => {
        this.error.set('Error al reportar da;os: ' + error.message);
      }
    });
  }

  // Usar repuesto
  usarRepuesto() {
    const jobId = this.trabajoId();
    const empId = this.empleadoId();
    if (!jobId || !empId || !this.repuestoForm.repuestoId) return;

    const requestBody = {
      empleadoId: empId,
      repuestoId: this.repuestoForm.repuestoId,
      cantidad: this.repuestoForm.cantidad,
      observaciones: this.repuestoForm.observaciones
    };

    this.http.post<any>(`http://localhost:8081/employee/job/${jobId}/usar-repuesto`, requestBody).subscribe({
      next: (response) => {
        if (response.status === 'success') {
          this.snackBar.open('Repuesto utilizado correctamente', 'Cerrar', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
          this.loadJobDetails();
          this.limpiarFormRepuesto();
        }
      },
      error: (error) => {
        this.error.set('Error al registrar uso de repuesto: ' + error.message);
      }
    });
  }

  // Métodos auxiliares
  limpiarFormAvance() {
    this.avanceForm = {
      descripcion: '',
      horasTrabajadas: null,
      tipo: 'AVANCE'
    };
  }

  limpiarFormApoyo() {
    this.apoyoForm = {
      motivo: '',
      tipoEspecialista: 'ELECTRICISTA'
    };
  }

  limpiarFormDanos() {
    this.danosForm = {
      descripcionDanos: '',
      severidad: 'MEDIO'
    };
  }

  limpiarFormRepuesto() {
    this.repuestoForm = {
      repuestoId: null,
      cantidad: 1,
      observaciones: ''
    };
  }

  getEstadoColor(estado: string): string {
    switch (estado?.toUpperCase()) {
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
    switch (estado?.toUpperCase()) {
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

  getProgressIcon(tipo: string): string {
    switch (tipo?.toUpperCase()) {
      case 'AVANCE':
        return 'trending_up';
      case 'SOLICITUD_APOYO':
        return 'help_outline';
      case 'DANOS_REPORTADOS':
        return 'report_problem';
      case 'USO_REPUESTO':
        return 'build_circle';
      case 'INICIADO':
        return 'play_arrow';
      case 'FINALIZADO':
        return 'check_circle';
      default:
        return 'info';
    }
  }

  goBack() {
    this.router.navigate(['/employee/jobs']);
  }


  trackByProgressId(index: number, item: JobProgress): number {
    return item.updateJobId;
  }
}
