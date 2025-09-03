import { Component, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenav } from '@angular/material/sidenav';
import { Router, RouterModule } from '@angular/router';
import { UserDTO } from '../../../interfaces/UserDTO';
import { AuthService } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-nav-users',
  imports: [CommonModule, MatSidenavModule, MatButtonModule, MatIconModule, RouterModule],
  templateUrl: './nav-users.html',
  styleUrl: './nav-users.css',
})
export class NavUsers {
  @ViewChild(MatSidenav) sidenav!: MatSidenav;

  toggleSidenav() {
    this.sidenav.toggle();
  }

  user_logged: UserDTO = JSON.parse(localStorage.getItem('user') || '{}');

  constructor(private router: Router, private authService: AuthService) {}
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
