import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TypeJobs } from '../../interfaces/TypeJobs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class TypeJobService {
  private url: string = 'http://localhost:8081/typeJobs';

  constructor(private http: HttpClient) {}

  public getAllTypeJobs(): Observable<TypeJobs[]> {
    return this.http.get<TypeJobs[]>(`${this.url}/all`);
  }
}
