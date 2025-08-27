import { Component } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { UserService } from '../../../services/user-service/User.service';
import { UserDTO } from '../../../interfaces/UserDTO';
// Importaciones de Angular Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RoleService } from '../../../services/Role.service';
import { RoleDTO } from '../../../interfaces/RoleDTO';

@Component({
  selector: 'app-list-users',
  imports: [MatTableModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule],
  templateUrl: './list-users.html',
  styleUrl: './list-users.css',
})
export class ListUsersComponent {

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

  dataSource: UserDTO[] = [];
  roles: RoleDTO[] = [];
  constructor(private userService: UserService, private roleService: RoleService) {
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
    const role = this.roles.find(r => r.rolId === roleId);
    return role ? role.nameRol : 'Desconocido';
  }
}
