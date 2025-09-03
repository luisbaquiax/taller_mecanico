import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { JobDTO } from '../../interfaces/JobDTO';

@Injectable({
  providedIn: 'root',
})
export class JobService {
  private url: string = 'http://localhost:8081/jobs';
  private jobSubject = new BehaviorSubject<JobDTO | null>(null);
  public job$: Observable<JobDTO | null> = this.jobSubject.asObservable();

  constructor(private http: HttpClient) {}

  public setJob(job: JobDTO) {
    this.jobSubject.next(job);
  }

  public getJob(): Observable<JobDTO | null> {
    return this.job$;
  }

  public getJobById(jobId: number): Observable<JobDTO> {
    return this.http.get<JobDTO>(`${this.url}/byId/${jobId}`);
  }

  public getJobs(): Observable<JobDTO[]> {
    return this.http.get<JobDTO[]>(`${this.url}/all`);
  }

  public getJobsByStatusId(statusId: number): Observable<JobDTO[]> {
    return this.http.get<JobDTO[]>(`${this.url}/jobsByStatusId/${statusId}`);
  }

  public saveJob(job: JobDTO, servicesSelected: number[]): Observable<JobDTO> {
    let params = new HttpParams();
    servicesSelected.forEach((service) => {
      params = params.append('servicesSelected', service.toString());
    });
    return this.http.post<JobDTO>(`${this.url}/save`, job, {
      params,
    });
  }

  public updateJob(job: JobDTO): Observable<JobDTO> {
    return this.http.put<JobDTO>(`${this.url}/update`, job);
  }
}
