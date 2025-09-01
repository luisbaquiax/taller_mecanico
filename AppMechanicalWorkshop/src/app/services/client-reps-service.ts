import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { QuotationDetailDTO } from '../interfaces/QuotationDetailDTO';
import { Observable } from 'rxjs';
import { PartsPerVehicleDTO } from '../interfaces/PartsPerVehicleDTO';
import { InvoicesPerClientDTO } from '../interfaces/InvoicesPerClientDTO';

@Injectable({
  providedIn: 'root'
})
export class ClientRepsService {

  private API_URL = 'http://localhost:8081/client';

  constructor(private http: HttpClient) { }

  getPartsByUser(userId: number): Observable<PartsPerVehicleDTO[]> {
    return this.http.get<PartsPerVehicleDTO[]>(`${this.API_URL}/${userId}/part`);
  }

  getPartsByUserAndVehicle(userId: number, vehicleId: number): Observable<PartsPerVehicleDTO[]> {
    return this.http.get<PartsPerVehicleDTO[]>(`${this.API_URL}/${userId}/part/vehicle/${vehicleId}`);
  }

  getInvoicesByUser(userId: number): Observable<InvoicesPerClientDTO[]> {
    return this.http.get<InvoicesPerClientDTO[]>(`${this.API_URL}/${userId}/invoices`);
  }

  getInvoicesByUserAndState(userId: number, status: string): Observable<InvoicesPerClientDTO[]> {
    return this.http.get<InvoicesPerClientDTO[]>(`${this.API_URL}/${userId}/invoices/state/${status}`);
  }

  getQuotationsByUser(userId: number): Observable<QuotationDetailDTO[]> {
    return this.http.get<QuotationDetailDTO[]>(`${this.API_URL}/${userId}/quotations`);
  }

  getQuotationsByUserAndState(userId: number, status: string): Observable<QuotationDetailDTO[]> {
    return this.http.get<QuotationDetailDTO[]>(`${this.API_URL}/${userId}/quotations/state/${status}`);
  }

  getActiveQuotationsByUser(userId: number): Observable<QuotationDetailDTO[]> {
    return this.http.get<QuotationDetailDTO[]>(`${this.API_URL}/${userId}/quotations/vigentes`);
  }
  

}
