import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RoleDTO } from '../interfaces/RoleDTO';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) { }

  /**
   * Robust method to get all roles from the backend.
   * @returns
   */
  getAllRoles(): Observable<RoleDTO[]> {
    return this.http.get<RoleDTO[]>('http://localhost:8081/roles/all');
  }
}
