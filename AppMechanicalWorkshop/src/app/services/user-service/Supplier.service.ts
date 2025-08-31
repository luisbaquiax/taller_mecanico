import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SupplierDTO } from '../../interfaces/SupplierDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SupplierService {
  private url = 'http://localhost:8081/suppliers';

  constructor(private http: HttpClient) {}

  public getAllSuppliers(): Observable<SupplierDTO[]> {
    return this.http.get<SupplierDTO[]>(`${this.url}/all`);
  }

  public findSupplierByUserId(userId: number): Observable<SupplierDTO> {
    return this.http.get<SupplierDTO>(`${this.url}/userId/${userId}`);
  }
}
