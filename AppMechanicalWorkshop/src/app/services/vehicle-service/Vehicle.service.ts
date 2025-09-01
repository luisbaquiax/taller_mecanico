import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { VehicleDTO } from '../../interfaces/VehicleDTO';

@Injectable({
  providedIn: 'root',
})
export class VehicleService {
  private apiUrl: string = 'http://localhost:8081/vehicles';

  constructor(private htt: HttpClient) {}

  /**
   * Get all vehicles
   * @returns All vehicles
   */
  public getAllVehicles(): Observable<VehicleDTO[]> {
    return this.htt.get<VehicleDTO[]>(`${this.apiUrl}/all`);
  }

  public getVehiclesWithoutClient(): Observable<VehicleDTO[]> {
    return this.htt.get<VehicleDTO[]>(`${this.apiUrl}/withoutClient`);
  }

  public saveVehicle(vehicle: VehicleDTO): Observable<VehicleDTO> {
    return this.htt.post<VehicleDTO>(`${this.apiUrl}/saveVehicle`, vehicle);
  }
}
