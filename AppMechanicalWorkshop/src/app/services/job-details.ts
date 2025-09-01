import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JobDetailDTO } from '../interfaces/JobDetailDTO';


@Injectable({
  providedIn: 'root'
})
export class JobDetails {

  private API_URL = 'http://localhost:8081/jobs';

  constructor(private http: HttpClient) { }


  getJobDetails(jobId: string): Observable<JobDetailDTO> {
    const url = `${this.API_URL}/${jobId}`;
    return this.http.get<JobDetailDTO>(url);
  }
  
  getAllJobDetails(): Observable<JobDetailDTO[]> {
    return this.http.get<JobDetailDTO[]>(`${this.API_URL}/details/all`);
  }

  getJobDetailsByUserId(userId: number): Observable<JobDetailDTO[]> {
    return this.http.get<JobDetailDTO[]>(`${this.API_URL}/details/user/${userId}`);
  }

  getJobDetailsByUserAndStatus(userId: number, statusId: number): Observable<JobDetailDTO[]> {
    const params = new HttpParams()
      .set('userId', userId.toString())
      .set('statusId', statusId.toString());

    return this.http.get<JobDetailDTO[]>(`${this.API_URL}/details/user-status`, { params });
  }


}
