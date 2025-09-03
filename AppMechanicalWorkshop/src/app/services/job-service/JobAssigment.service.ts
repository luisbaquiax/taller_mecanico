import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JobAssigmentDTO } from '../../interfaces/JobAssigmentDTO';

@Injectable({
  providedIn: 'root',
})
export class JobAssigmentService {
  private url: string = 'http://localhost:8081/jobAssigments';

  constructor(private http: HttpClient) {}

  public getJobAssigmentsByJob(jobId: number): Observable<JobAssigmentDTO[]> {
    return this.http.get<JobAssigmentDTO[]>(`${this.url}/byJob/${jobId}`);
  }
}
