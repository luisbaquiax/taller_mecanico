import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { StatusJobsDTO } from '../../interfaces/StatusJobsDTO';

@Injectable({
  providedIn: 'root',
})
export class StatusJobsService {
  private url: string = 'http://localhost:8081/statusJobs';

  constructor(private http: HttpClient) {}

  public getAllStatusJobs(): Observable<StatusJobsDTO[]> {
    return this.http.get<StatusJobsDTO[]>(`${this.url}/all`);
  }
}
