import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JobServicesDTO } from '../../interfaces/JobServicesDTO';

@Injectable({
  providedIn: 'root'
})
export class JobServicesService {
  private url: string = 'http://localhost:8081/jobServices'
  constructor(private http: HttpClient) { }

  public getJobServicesByJob(jobId: number): Observable<JobServicesDTO[]> {
    return this.http.get<JobServicesDTO[]>(`${this.url}/jobServicesByJobId/${jobId}`);
  }
}
