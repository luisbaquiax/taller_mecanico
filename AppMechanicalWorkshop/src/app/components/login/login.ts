import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../services/user-service/User.service';
import { Router, RouterModule } from '@angular/router';
import { UserDTO } from '../../interfaces/UserDTO';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Login {
  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  showMessage = signal(false);
  message = signal('');

  hide = signal(true);

  constructor(private userService: UserService, private router: Router) {}

  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const username = this.loginForm.get('username')?.value;
      const password = this.loginForm.get('password')?.value;
      console.log('Username:', username);
      console.log('Password:', password);
      this.userService.getUserByUsernamePassword(username!, password!).subscribe({
        next: (data: UserDTO) => {
          localStorage.setItem('currentUser', JSON.stringify(data));
          localStorage.setItem('userId', data.userId?.toString() || '');
          localStorage.setItem('userRole', data.rolId?.toString() || '');
          // Aquí puedes manejar la respuesta del servicio, por ejemplo, guardar el token y redirigir al usuario
          switch (data.rolId) {
            case 1:
              this.router.navigate(['/admin']);
              break;
            case 2:
              this.router.navigate(['/employee']);
              break;
            case 3:
              //this.router.navigate(['/specialist-nav']);
              break;
            case 4:
              //this.router.navigate(['/client-nav']);
              break;
            default:
              //rol desconocido
              break;
          }
        },
        error: (error) => {
          if (error.status === 401 || error.status === 404) {
            this.showMessage.set(true);
            this.message.set('Usuario o contraseña incorrectos');
          } else {
            this.showMessage.set(true);
            this.message.set('Error en el servidor, intente más tarde');
          }
        },
      });
    }
  }
}
