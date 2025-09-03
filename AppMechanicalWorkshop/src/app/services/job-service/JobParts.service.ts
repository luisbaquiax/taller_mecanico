import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JobPartsDTO } from '../../interfaces/JobPartsDTO';

@Injectable({
  providedIn: 'root'
})
export class JobPartsService {
  private usrl: string = 'http://localhost:8081/jobParts'
  constructor(private http: HttpClient) { }

  public getPartsByJob(jobId: number): Observable<JobPartsDTO[]> {
    return this.http.get<JobPartsDTO[]>(`${this.usrl}/all`);
  }

}
