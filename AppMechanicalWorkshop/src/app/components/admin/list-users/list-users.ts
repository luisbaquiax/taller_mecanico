import { Component, OnInit } from '@angular/core';

// Importaciones de Angular Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';

//importaciones de servicios
import { UserService } from '../../../services/user-service/User.service';
import { UserDTO } from '../../../interfaces/UserDTO';
import { RoleService } from '../../../services/Role.service';
import { RoleDTO } from '../../../interfaces/RoleDTO';
@Component({
  selector: 'app-list-users',
  imports: [MatTableModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule],
  templateUrl: './list-users.html',
  styleUrl: './list-users.css',
})
export class ListUsersComponent implements OnInit {
  displayedColumns: string[] = [
    'userId',
    'rolId',
    'username',
    'email',
    'phone',
    'name',
    'lastName',
    'isActive',
    'actions',
  ];

  durationSecond: number = 5000;

  dataSource: UserDTO[] = [];
  roles: RoleDTO[] = [];
  constructor(
    private userService: UserService,
    private roleService: RoleService,
    private _snack: MatSnackBar
  ) {

  }

  ngOnInit(): void {
    this.setDataSource();
    this.setRoles();
  }

  setDataSource(): void {
    this.userService.getAllUsers().subscribe((data: UserDTO[]) => {
      this.dataSource = data;
      console.log(this.dataSource);
    });
  }

  setRoles(): void {
    this.roleService.getAllRoles().subscribe((data: RoleDTO[]) => {
      this.roles = data;
      console.log(this.roles);
    });
  }

  getRoleName(roleId: number): string {
    const role = this.roles.find((r) => r.rolId === roleId);
    return role ? role.nameRol : 'Desconocido';
  }

  updateUser(user: UserDTO): void {
    if (confirm('¿Esta seguro de actualizar el usuario?')) {
      user.active = !user.active;
      this.userService.updateUser(user).subscribe({
        next: (data: UserDTO) => {
          this.setDataSource();
          this._snack.open('Usuario actualizado correctamente', 'Cerrar', {
            duration: this.durationSecond,
          });
        },
        error: (error) => {
          this._snack.open('Error al actualizar el usuario', 'Cerrar', {
            duration: this.durationSecond,
          });
        },
      });
    }
  }

  getUsersByIsActive(isActive: boolean): void {
    this.userService.getUserByIsActive(isActive).subscribe((data: UserDTO[]) => {
      this.dataSource = data;
    });
  }
}
