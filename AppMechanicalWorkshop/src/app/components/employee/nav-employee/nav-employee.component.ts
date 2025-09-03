import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common'; // ← AGREGAR
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenav } from '@angular/material/sidenav';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { filter } from 'rxjs';

@Component({
  selector: 'app-nav-employee',
  imports: [CommonModule, MatSidenavModule, MatButtonModule, MatIconModule, RouterModule],
  templateUrl: './nav-employee.component.html',
  styleUrl: './nav-employee.component.css',
})
export class NavEmployeeComponent implements OnInit {

  @ViewChild(MatSidenav) sidenav!: MatSidenav;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {

  }

  toggleSidenav() {
    this.sidenav.toggle();
  }

  goToProfile(): void {
    this.router.navigate(['/user/profile']);
  }

  // Información del usuario actual
  getCurrentUserName(): string {
    return this.authService.getCurrentUserName();
  }

  getCurrentUserRole(): string {
    return this.authService.getCurrentUserRoleName();
  }

  get currentEmployee() {
    return this.authService.getCurrentUser();
  }

  get employeeName(): string {
    return this.authService.getCurrentUserName();
  }

  // Metodo para cerrar sesión
  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('userId');
    localStorage.removeItem('userRole');
    this.router.navigate(['/']);
  }

}
