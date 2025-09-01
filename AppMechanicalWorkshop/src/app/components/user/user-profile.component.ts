import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user-service/User.service';
import { Router } from '@angular/router';
import { UserDTO } from '../../interfaces/UserDTO';

interface TwoFactorType {
  id: number;
  name: string;
  description: string;
}

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatSlideToggleModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  profile = signal<UserDTO | null>(null);
  twoFactorTypes = signal<TwoFactorType[]>([
    { id: 1, name: 'SMS', description: 'Código por mensaje de texto' },
    { id: 2, name: 'Email', description: 'Código por correo electrónico' }
  ]);

  isLoading = signal(false);
  isSaving = signal(false);
  error = signal<string | null>(null);

  // Form data - usando la misma estructura que UserDTO
  formData = signal({
    name: '',
    lastName: '',
    email: '',
    phone: '',
    twoFactorAuth: false,
    typeTwoFactorId: 2
  });

  // Cambio de contraseña
  passwordForm = signal({
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
  });

  showPasswordForm = signal(false);

  // Info del usuario actual
  currentUserRole = signal<string>('');
  dashboardRoute = signal<string>('/dashboard');

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private userService: UserService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadProfile();
    this.setupUserRoleInfo();
  }

  setupUserRoleInfo() {
    // Usar los nuevos metodos del AuthService
    this.currentUserRole.set(this.authService.getCurrentUserRoleName());
    this.dashboardRoute.set(this.authService.getDashboardRoute());
  }

  loadProfile() {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser?.userId) {
      this.router.navigate(['/login']);
      return;
    }
    this.isLoading.set(true);
    this.error.set(null);
    this.http.get<UserDTO>(`http://localhost:8081/users/${currentUser.userId}`).subscribe({
      next: (response) => {
        this.profile.set(response);
        this.formData.set({
          name: response.name || '',
          lastName: response.lastName || '',
          email: response.email || '',
          phone: response.phone || '',
          twoFactorAuth: response.twoFactorAuth || false,
          typeTwoFactorId: response.typeTwoFactorId || 2
        });
        this.isLoading.set(false);
      },
      error: (error) => {
        this.error.set('Error al cargar el perfil: ' + error.message);
        this.isLoading.set(false);
      }
    });
  }

  saveProfile() {
    const currentProfile = this.profile();
    if (!currentProfile) return;

    this.isSaving.set(true);
    this.error.set(null);

    const updateData: UserDTO = {
      userId: currentProfile.userId,
      rolId: currentProfile.rolId,
      username: currentProfile.username,
      active: currentProfile.active,
      name: this.formData().name,
      lastName: this.formData().lastName,
      email: this.formData().email,
      phone: this.formData().phone,
      twoFactorAuth: this.formData().twoFactorAuth,
      typeTwoFactorId: this.formData().typeTwoFactorId
    };

    this.userService.updateUser(updateData).subscribe({
      next: (response: any) => {
        this.profile.set(response);
        this.isSaving.set(false);
        this.snackBar.open('Perfil actualizado correctamente', 'Cerrar', {
          duration: 3000,
          panelClass: ['success-snackbar']
        });

        this.authService.updateCurrentUser(response);
      },
      error: (error: { message: string; }) => {
        this.error.set('Error al actualizar perfil: ' + error.message);
        this.isSaving.set(false);
        this.snackBar.open('Error al actualizar perfil', 'Cerrar', {
          duration: 3000,
          panelClass: ['error-snackbar']
        });
      }
    });
  }

  changePassword() {
    const passwordData = this.passwordForm();

    if (passwordData.newPassword !== passwordData.confirmPassword) {
      this.snackBar.open('Las contraseñas no coinciden', 'Cerrar', {
        duration: 3000,
        panelClass: ['error-snackbar']
      });
      return;
    }

    if (passwordData.newPassword.length < 6) {
      this.snackBar.open('La contraseña debe tener al menos 4 caracteres', 'Cerrar', {
        duration: 3000,
        panelClass: ['error-snackbar']
      });
      return;
    }

    this.isSaving.set(true);

    const changePasswordData = {
      userId: this.profile()?.userId,
      currentPassword: passwordData.currentPassword,
      newPassword: passwordData.newPassword
    };

    this.http.put<any>('http://localhost:8081/users/changePassword', changePasswordData).subscribe({
      next: (response) => {
        this.isSaving.set(false);
        this.showPasswordForm.set(false);
        this.passwordForm.set({
          currentPassword: '',
          newPassword: '',
          confirmPassword: ''
        });
        this.snackBar.open('Contraseña cambiada correctamente', 'Cerrar', {
          duration: 3000,
          panelClass: ['success-snackbar']
        });
      },
      error: (error) => {
        this.isSaving.set(false);
        this.snackBar.open('Error al cambiar contraseña: ' + error.message, 'Cerrar', {
          duration: 3000,
          panelClass: ['error-snackbar']
        });
      }
    });
  }

  testTwoFactor() {
    if (!this.formData().twoFactorAuth) {
      this.snackBar.open('Primero activa la autenticación 2FA', 'Cerrar', {
        duration: 3000,
        panelClass: ['warning-snackbar']
      });
      return;
    }

    this.isSaving.set(true);

    const testData = {
      userId: this.profile()?.userId,
      email: this.formData().email,
      phone: this.formData().phone,
      type: this.formData().typeTwoFactorId
    };

    this.http.post<any>('http://localhost:8081/users/test2fa', testData).subscribe({
      next: (response) => {
        this.isSaving.set(false);
        this.snackBar.open(
          `Código de prueba enviado: ${response.testCode || 'Enviado correctamente'}`,
          'Cerrar',
          { duration: 5000, panelClass: ['success-snackbar'] }
        );
      },
      error: (error) => {
        this.isSaving.set(false);
        this.snackBar.open('Error al enviar código de prueba', 'Cerrar', {
          duration: 3000,
          panelClass: ['error-snackbar']
        });
      }
    });
  }

  goBack() {
    this.router.navigate([this.dashboardRoute()]);
  }

  updateFormData(field: string, value: any) {
    this.formData.update(current => ({
      ...current,
      [field]: value
    }));
  }

  updatePasswordForm(field: string, value: string) {
    this.passwordForm.update(current => ({
      ...current,
      [field]: value
    }));
  }

  togglePasswordForm() {
    this.showPasswordForm.update(current => !current);
    if (!this.showPasswordForm()) {
      this.passwordForm.set({
        currentPassword: '',
        newPassword: '',
        confirmPassword: ''
      });
    }
  }

  getTwoFactorTypeName(typeId: number): string {
    const type = this.twoFactorTypes().find(t => t.id === typeId);
    return type ? type.name : 'Desconocido';
  }
}
