import { Component } from '@angular/core';
import { CurrencyPipe, DatePipe, TitleCasePipe } from '@angular/common';

// Importaciones de Angular Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';

// Importaciones locales
import { PartService } from '../../../services/Part.service';
import { PartDTO } from '../../../interfaces/PartDTO';

@Component({
  selector: 'app-list-parts',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    CurrencyPipe,
  ],
  templateUrl: './list-parts.html',
  styleUrl: './list-parts.css',
})
export class ListPartsComponent {
  displayedColumns: string[] = [
    'partId',
    'supplierId',
    'namePart',
    'brandPart',
    'descriptionPart',
    'costPrice',
    'salePrice',
    'stockPart',
    'isActive',
    'actions',
  ];
  dataSource: PartDTO[] = [];
  constructor(private partService: PartService, private _snackBar: MatSnackBar) {
    this.getAllParts();
  }

  getAllParts():void {
    this.partService.getParts().subscribe({
      next: (data: PartDTO[]) => {
        this.dataSource = data;
      },
      error: (error: any) => {
        console.error('Error al obtener partes:', error);
        this._snackBar.open('Error al cargar los repuestos', 'Cerrar', {
          duration: 3000,
        });
      },
    });
  }
  getPartsByIsActive(isActive: boolean): void {
    this.partService.getPartsByIsActive(isActive).subscribe((data: PartDTO[]) => {
      this.dataSource = data;
    });
  }

  updatePart(part: PartDTO): void {
    if(confirm('¿Esta seguro de actualizar el repuesto?')){
      part.active = !part.active;
    this.partService.savePart(part).subscribe({
      next: (data: PartDTO) => {
        this.getAllParts();
        this._snackBar.open('Repuesto actualizado correctamente', 'Cerrar', {
          duration: 5000,
        });
      },
      error: (error) => {
        this._snackBar.open('Error al actualizar el repuesto', 'Cerrar', {
          duration: 5000,
        });
      },
    });
    }

  }
}
