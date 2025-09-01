import { Component, OnInit, ChangeDetectionStrategy, inject, model, signal } from '@angular/core';
import { CurrencyPipe, DatePipe, TitleCasePipe } from '@angular/common';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';

// Importaciones de Angular Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';

//formularios reactivos
import { FormGroup, Validators, FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ServiceTypesService } from '../../../services/job-service/ServiceTypes.service';
import { ServiceTypesDTO } from '../../../interfaces/ServiceTypesDTO';

@Component({
  selector: 'app-list-services',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    CurrencyPipe,
    MatButtonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './list-services.html',
  styleUrl: './list-services.css',
})
export class ListServicesComponent implements OnInit {
  displayedColumns: string[] = [
    'serviceTypeId',
    'nameServiceType',
    'descriptionServiceType',
    'basePrice',
    'actions',
  ];

  dataSource: ServiceTypesDTO[] = [];
  loading: boolean = true;
  error: string | null = null;
  registerForm: FormGroup;
  showForm: boolean = false;
  isEditing: boolean = false;
  newService: ServiceTypesDTO = {
    serviceTypeId: 0,
    nameServiceType: '',
    descriptionServiceType: '',
    basePrice: 0,
  };

  constructor(
    private _snackBar: MatSnackBar,
    private fb: FormBuilder,
    private serviceTypesService: ServiceTypesService
  ) {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.setServices();
  }

  setServices(): void {
    this.serviceTypesService.getAll().subscribe({
      next: (data: ServiceTypesDTO[]) => {
        this.dataSource = data;
        this.loading = false;
        this.error = null;
      },
      error: (error) => {
        if (error.status === 500) {
          this.loading = false;
          this.error = 'El servidor se encuentra en mantenimiento';
          this._snackBar.open('El servidor está fuera de servicio.', 'Aceptar', {
            duration: 5000,
          });
        }
      },
    });
  }

  showFormService(): void {
    this.showForm = !this.showForm;
  }

  onSubmit(): void {
    if(this.registerForm.valid){
      this.newService.nameServiceType = this.registerForm.value.name;
      this.newService.descriptionServiceType = this.registerForm.value.description;
      this.newService.basePrice = this.registerForm.value.price;

      let message = 'Servicio actualizado con exito.';
      if(!this.isEditing){
        this.newService.serviceTypeId = 0;
        message = 'Servicio registrado con exito.'
      }

      this.serviceTypesService.saveServiceType(this.newService).subscribe({
        next: (data: ServiceTypesDTO) => {
          this._snackBar.open(message, 'Cerrar', {
            duration: 5000,
          });
          this.showForm = false;
          this.registerForm.reset();
          this.setServices();
        },
        error: (error) => {
          console.log(error);
          this._snackBar.open('Error al registrar el servicio', 'Cerrar', {
            duration: 5000,
          });
        },
      });
    }
  }

  editService(serviceTypeId: number): void {
    const service = this.dataSource.find((s) => s.serviceTypeId === serviceTypeId);
    if (service) {
      this.isEditing = true;
      this.newService.serviceTypeId = service.serviceTypeId;
      this.registerForm.patchValue({
        name: service.nameServiceType,
        description: service.descriptionServiceType,
        price: service.basePrice,
      });
      this.showForm = true;
    }
  }

}
