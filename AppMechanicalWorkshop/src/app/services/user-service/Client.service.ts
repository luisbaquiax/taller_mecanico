import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ClientDTO } from '../../interfaces/ClientDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private url: string = 'http://localhost:8081/clients'

  constructor(private http: HttpClient) { }

  getAllClients(): Observable<ClientDTO[]> {
    return this.http.get<ClientDTO[]>(`${this.url}/all`);
  }

}
