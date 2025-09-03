import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceTypesDTO } from '../../interfaces/ServiceTypesDTO';
import { ServiceType } from '../../interfaces/ServiceType';

@Injectable({
  providedIn: 'root',
})
export class ServiceTypesService {
  private url: string = 'http://localhost:8081/serviceType';

  constructor(private http: HttpClient) {}

  /**
   * Get all service types
   * @returns
   */
  public getAll(): Observable<ServiceTypesDTO[]> {
    return this.http.get<ServiceTypesDTO[]>(`${this.url}/all1`);
  }

  /**
   * Save a new service type
   * @param serviceTypeServiceTypesDTO
   * @returns
   */
  public saveServiceType(serviceType: ServiceTypesDTO): Observable<ServiceTypesDTO> {
    return this.http.post<ServiceTypesDTO>(`${this.url}/save`, serviceType);
  }
}
