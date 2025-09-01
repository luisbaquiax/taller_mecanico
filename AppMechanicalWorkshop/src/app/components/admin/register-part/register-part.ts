import { Component, OnInit, signal } from '@angular/core';
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

// Importaciones locales
import { UserService } from '../../../services/user-service/User.service';
import { UserDTO } from '../../../interfaces/UserDTO';
import { Roles } from '../../../enums/Roles';
import { PartService } from '../../../services/Part.service';
import { PartDTO } from '../../../interfaces/PartDTO';
import { SupplierService } from '../../../services/user-service/Supplier.service';
import { SupplierDTO } from '../../../interfaces/SupplierDTO';

@Component({
  selector: 'app-register-part',
  imports: [
    CommonModule,
    RouterModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    ReactiveFormsModule,
  ],
  templateUrl: './register-part.html',
  styleUrl: './register-part.css',
})
export class RegisterPartComponenet implements OnInit {
  registerForm: FormGroup;

  supplieres: SupplierDTO[] = [];
  supplier!: SupplierDTO;
  users: UserDTO[] = [];

  partDTO: PartDTO = {
    partId: 0,
    supplierId: 0,
    namePart: '',
    brandPart: '',
    descriptionPart: '',
    costPrice: 0,
    salePrice: 0,
    stockPart: 0,
    active: true,
  };

  constructor(
    private fb: FormBuilder,
    private _snackBar: MatSnackBar,
    private userService: UserService,
    private supplierService: SupplierService,
    private partService: PartService
  ) {
    this.registerForm = this.fb.group({
      userId: ['', Validators.required],
      namePart: ['', [Validators.required, Validators.maxLength(100)]],
      brandPart: ['', Validators.required],
      descriptionPart: ['', Validators.required],
      costPrice: ['', [Validators.required, Validators.min(0)]],
      salePrice: ['', [Validators.required, Validators.min(0)]],
      stockPart: ['', [Validators.required, Validators.min(0)]],
    });

  }

  ngOnInit() {
      this.setSupplies();
      this.setUser();
  }

  onSubmit() {
    if (this.registerForm.valid) {

      this.partDTO.supplierId = this.registerForm.value.userId;
      this.partDTO.namePart = this.registerForm.value.namePart;
      this.partDTO.brandPart = this.registerForm.value.brandPart;
      this.partDTO.descriptionPart = this.registerForm.value.descriptionPart;
      this.partDTO.costPrice = this.registerForm.value.costPrice;
      this.partDTO.salePrice = this.registerForm.value.salePrice;
      this.partDTO.stockPart = this.registerForm.value.stockPart;

      //send data
      this.partService.savePart(this.partDTO).subscribe({
        next: (data: any) => {
          this._snackBar.open('Repuesto registrado correctamente', 'Cerrar', {
            duration: 5000,
          });
          this.registerForm.reset();
            this.partDTO = {
              partId: 0,
              supplierId: 0,
              namePart: '',
              brandPart: '',
              descriptionPart: '',
              costPrice: 0,
              salePrice: 0,
              stockPart: 0,
              active: true,
            };
        },
        error: (error) => {
          console.log(error);
          if (error.status === 402) {
            this._snackBar.open(`${error.error.message}`, 'Cerrar', {
              duration: 5000,
            });
          } else {
            this._snackBar.open('Error al registrar el repuesto', 'Cerrar', {
              duration: 5000,
            });
          }
        },
      });
    }
  }

  setSupplies(): void {
   this.supplierService.getAllSuppliers().subscribe((data: SupplierDTO[]) => {
     this.supplieres = data;
   });
  }

  setUser(): void {
    this.userService.getAllUsers().subscribe((data: UserDTO[]) => {
      this.users = data;
    });
  }

  getNameSupplier(userId: number): string {
    const user = this.users.find((s) => s.userId === userId);
    if (user) {
      return user.name;
    }
    return 'Desconocido';
  }

}
