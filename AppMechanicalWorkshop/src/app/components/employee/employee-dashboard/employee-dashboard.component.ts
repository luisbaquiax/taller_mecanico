import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatProgressBarModule,
  ],
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.css']
})
export class EmployeeDashboardComponent implements OnInit {

  dashboardData = signal<any>(null);
  isLoading = signal(true);
  error = signal<string | null>(null);
  empleado = signal<any>(null);
  stats = signal({
    trabajosActivos: 0,
    trabajosCompletados: 0,
    trabajosPendientes: 0,
    horasTrabajadas: 0
  });

  constructor(
    private router: Router,
    private http: HttpClient,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.loadUserData();
    this.loadDashboardData();
    this.loadEmployeeStats();
  }

  // Cargar datos del usuario actual usando TU UserDTO
  loadUserData() {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser) {
      this.empleado.set({
        id: currentUser.userId,
        name: currentUser.name + ' ' + currentUser.lastName,
        role: this.getRoleName(currentUser.rolId),
        email: currentUser.email
      });
    } else {
      const userId = localStorage.getItem('userId');
      const userData = localStorage.getItem('currentUser');
      if (userId && userData) {
        try {
          const user = JSON.parse(userData);
          this.empleado.set({
            id: parseInt(userId),
            name: user.name + ' ' + user.lastName,
            role: this.getRoleName(user.rolId),
            email: user.email
          });
        } catch (error) {
          console.error('Error parsing user data:', error);
        }
      }
    }
  }

  getRoleName(rolId: number): string {
    switch (rolId) {
      case 1: return 'ADMIN';
      case 2: return 'EMPLEADO';
      case 3: return 'ESPECIALISTA';
      case 4: return 'CLIENTE';
      case 5: return 'PROVEEDOR';
      default: return 'DESCONOCIDO';
    }
  }

  loadDashboardData() {
    this.isLoading.set(true);
    this.http.get<any>('http://localhost:8081/employee/dashboard').subscribe({
      next: (response) => {
        this.dashboardData.set(response);
        this.isLoading.set(false);
      },
      error: (error) => {
        this.error.set('Error al cargar el dashboard: ' + error.message);
        this.isLoading.set(false);
      }
    });
  }

  loadEmployeeStats() {
    const empleadoId = this.empleado()?.id;
    if (!empleadoId) {
      console.warn('No se pudo obtener el ID del empleado');
      return;
    }

    this.http.get<any>(`http://localhost:8081/employee/jobs/${empleadoId}`).subscribe({
      next: (response) => {
        if (response.status === 'success' && response.trabajos) {
          const trabajos = response.trabajos;
          const activos = trabajos.filter((t: any) => t.estadoTrabajo === 'En progreso').length;
          const completados = trabajos.filter((t: any) => t.estadoTrabajo === 'Completado').length;
          const pendientes = trabajos.filter((t: any) => t.estadoTrabajo === 'Pendiente').length;
          const horasTotal = trabajos
            .filter((t: any) => t.estadoTrabajo === 'Completado')
            .reduce((total: number, t: any) => total + (t.horasEstimadas || 0), 0);

          this.stats.set({
            trabajosActivos: activos,
            trabajosCompletados: completados,
            trabajosPendientes: pendientes,
            horasTrabajadas: horasTotal
          });
        }
      },
      error: (error) => {
        console.error('Error al cargar estadísticas del empleado:', error);
      }
    });
  }

  navigateToJobs() {
    this.router.navigate(['/employee/jobs']);
  }

  navigateToProfile() {
    this.router.navigate(['/employee/perfil']);
  }

  navigateToHistory() {
    this.router.navigate(['/employee/history']);
  }

  // Refrescar dashboard
  refreshDashboard() {
    this.loadUserData();
    this.loadDashboardData();
    this.loadEmployeeStats();
  }
}
