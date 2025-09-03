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
import {
  MatSnackBar,
  MatSnackBarAction,
  MatSnackBarActions,
  MatSnackBarLabel,
  MatSnackBarRef,
} from '@angular/material/snack-bar';
// Importaciones de formularios reactivos
import { FormGroup, Validators, ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { F, V } from '@angular/cdk/keycodes';
import { PartDTO } from '../../../interfaces/PartDTO';
import { PartService } from '../../../services/Part.service';

@Component({
  selector: 'app-add-inventory',
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
  templateUrl: './add-inventory.html',
  styleUrl: './add-inventory.css',
})
export class AddInventoryComponent implements OnInit {
  registerForm!: FormGroup;
  parts: PartDTO[] = [];
  partNew: PartDTO = {
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
    private _snackBar: MatSnackBar,
    private fb: FormBuilder,
    private partService: PartService
  ) {
    this.registerForm = this.fb.group({
      parId: ['', Validators.required],
      stockPart: ['', [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit() {
    this.getAllParts();
  }

  getAllParts(): void {
    this.partService.getParts().subscribe({
      next: (data: PartDTO[]) => {
        this.parts = data;
      },
      error: (error: any) => {
        console.error('Error al obtener partes:', error);
        this._snackBar.open('Error al cargar los repuestos', 'Cerrar', {
          duration: 3000,
        });
      },
    });
  }

  onSubmit(){
    if(this.registerForm.valid){
      if(confirm('¿Desea registar la cantidad del repuesto en el inventario?')){
        const partId = this.registerForm.value.parId;
        const stockPart = this.registerForm.value.stockPart;
        const part = this.parts.find((element) => element.partId === partId );
        if(part){
          part.stockPart += stockPart;
          this.partService.updatePart(part, true).subscribe({
            next: (data: any) => {
              this._snackBar.open(`Stock actualizado correctamente para el repuesto ${part.namePart}`, 'Cerrar', {
                duration: 5000,
              });
              this.resetValues();
            },
            error: (error) => {
              console.log(error);
              this._snackBar.open('Error al registrar el repuesto', 'Cerrar', {
                duration: 5000,
              });
            },
          });
        }
      }
    }
  }

  resetValues(){
    this.registerForm.reset();
    this.partNew = {
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
  }
}
