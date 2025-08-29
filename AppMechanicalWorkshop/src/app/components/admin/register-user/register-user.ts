import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatFormField } from '@angular/material/form-field';
import { MatLabel } from '@angular/material/form-field';
import { MatError } from '@angular/material/form-field';
import { MatIcon } from '@angular/material/icon';

// Importaciones de Angular Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatOption } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select';
// Importaciones de formularios reactivos
import { FormGroup, Validators, ReactiveFormsModule, FormBuilder } from '@angular/forms';
import {
  MatSnackBar,
  MatSnackBarAction,
  MatSnackBarActions,
  MatSnackBarLabel,
  MatSnackBarRef,
} from '@angular/material/snack-bar';
import { F, V } from '@angular/cdk/keycodes';
import { RegisterClientDTO } from '../../../interfaces/RegisterClient';
import { Roles } from '../../../enums/Roles';
import { UserService } from '../../../services/user-service/User.service';
import { UserDTO } from '../../../interfaces/UserDTO';
import { RoleDTO } from '../../../interfaces/RoleDTO';
import { RoleService } from '../../../services/Role.service';
import { SupplierType } from '../../../enums/SupplierType';
import { UserRegisterDTO } from '../../../interfaces/UserRegisterDTO';

@Component({
  selector: 'app-register-user',
  imports: [
    CommonModule,
    RouterModule,
    MatFormField,
    MatLabel,
    MatError,
    MatIcon,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatSelectModule,
  ],
  standalone: true,
  templateUrl: './register-user.html',
  styleUrl: './register-user.css',
})
export class RegisterUser {
  hide = signal(true);
  registerForm: FormGroup;

  clientNew: RegisterClientDTO = {
    rolId: 0,
    username: '',
    email: '',
    password: '',
    name: '',
    lastName: '',
    phone: '',
    nit: '',
    address: '',
  };

  userNew: UserRegisterDTO = {
    username: '',
    rolId: 0,
    password: '',
    email: '',
    phone: '',
    name: '',
    lastName: '',
  };

  durationInSeconds = 5;
  roles: RoleDTO[] = [];

  selectedRole: number = 0;
  shwoInputNit: boolean = false;
  showTypeSupplier: boolean = false;
  supplierTypes: string[] = [
    SupplierType.getName(SupplierType.PERSON),
    SupplierType.getName(SupplierType.COMPANY),
  ];

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private _snackBar: MatSnackBar,
    private roleService: RoleService
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      name: ['', [Validators.required, Validators.maxLength(100)]],
      last_name: ['', [Validators.required, Validators.maxLength(100)]],
      phone: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(8),
          Validators.pattern('^[0-9]*$'),
        ],
      ],
      nit: [
        '00000000',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(8),
          Validators.pattern('^[0-9]*$'),
        ],
      ],
      address: ['dirección', Validators.required],
      rolId: ['', Validators.required],
      supplierType: [SupplierType.getName(SupplierType.COMPANY), Validators.required],
    });

    this.setRoles();
  }

  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }

  private setRoles(): void {
    this.roleService.getAllRoles().subscribe((data: RoleDTO[]) => {
      this.roles = data;
    });
  }

  onSubmit() {
    this.selectedRole = this.registerForm.get('rolId')?.value;
    if (this.registerForm.valid) {
      console.log('enviando datos...');
      if (this.selectedRole === Roles.getId(Roles.CLIENT)) {
        this.registerClient();
      } else if (this.selectedRole === Roles.getId(Roles.SUPPLIER)) {
        this.registerEmployee(this.registerForm.get('supplierType')?.value);
      } else {
        this.registerEmployee('employee');
      }
    } else {
      console.log(this.registerForm.errors);
    }
  }

  verifyRole() {
    this.selectedRole = this.registerForm.get('rolId')?.value;
    this.shwoInputNit = this.selectedRole === Roles.getId(Roles.CLIENT);
    this.showTypeSupplier = this.selectedRole === Roles.getId(Roles.SUPPLIER);
  }

  registerClient() {
    this.clientNew.rolId = this.registerForm.get('rolId')?.value;
    this.clientNew.username = this.registerForm.get('username')?.value;
    this.clientNew.email = this.registerForm.get('email')?.value;
    this.clientNew.password = this.registerForm.get('password')?.value;
    this.clientNew.name = this.registerForm.get('name')?.value;
    this.clientNew.lastName = this.registerForm.get('last_name')?.value;
    this.clientNew.phone = this.registerForm.get('phone')?.value;
    this.clientNew.nit = this.registerForm.get('nit')?.value;
    this.clientNew.address = this.registerForm.get('address')?.value;

    this.userService.regiterClient(this.clientNew).subscribe({
      next: (data: UserDTO) => {
        this._snackBar.open('Cuenta creada exitosamente', 'Cerrar', {
          duration: this.durationInSeconds * 1000,
        });
        this.resetForm();
      },
      error: (error) => {
        if (error.status === 409) {
          this._snackBar.open(`${error.error.message}`, 'Cerrar', {
            duration: this.durationInSeconds * 1000,
          });
        } else {
          this._snackBar.open('Error al crear la cuenta', 'Cerrar', {
            duration: this.durationInSeconds * 1000,
          });
        }
      },
    });
  }

  registerEmployee(supplierType: string) {
    this.userNew.rolId = this.registerForm.get('rolId')?.value;
    this.userNew.username = this.registerForm.get('username')?.value;
    this.userNew.password = this.registerForm.get('password')?.value;
    this.userNew.email = this.registerForm.get('email')?.value;
    this.userNew.name = this.registerForm.get('name')?.value;
    this.userNew.lastName = this.registerForm.get('last_name')?.value;
    this.userNew.phone = this.registerForm.get('phone')?.value;
    console.log( 'this.userNew ', this.userNew);
    this.userService.registerUser(this.userNew, supplierType).subscribe({
      next: (data: UserDTO) => {
        this._snackBar.open('Usuario registrado exitosamente', 'Cerrar', {
          duration: this.durationInSeconds * 1000,
        });
        this.resetForm();
      },
      error: (error) => {
        if (error.status === 409) {
          this._snackBar.open(`${error.error.message}`, 'Cerrar', {
            duration: this.durationInSeconds * 1000,
          });
        } else {
          this._snackBar.open('Error al registrar el usuario', 'Cerrar', {
            duration: this.durationInSeconds * 1000,
          });
        }
      },
    });
  }

  resetForm(){
    this.registerForm.reset();
    this.registerForm.get('nit')?.setValue('00000000');
    this.registerForm.get('supplierType')?.setValue(SupplierType.getName(SupplierType.COMPANY));
    this.registerForm.get('address')?.setValue('direccion`');
  }
}
