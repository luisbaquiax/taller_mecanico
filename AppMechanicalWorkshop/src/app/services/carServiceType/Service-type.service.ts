import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ServiceType } from '../../interfaces/ServiceType';

@Injectable({
  providedIn: 'root'
})
export class ServiceTypeService {

  private apiUrl: string = 'http://localhost:8081/serviceType';

  constructor(private http: HttpClient) { }


  getAll(): Observable<ServiceType[]> {
    return this.http.get<ServiceType[]>(this.apiUrl + '/all');
  }

  create(serviceType: ServiceType): Observable<ServiceType> {
    return this.http.post<ServiceType>(this.apiUrl, serviceType);
  }

}
