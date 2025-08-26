import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDTO } from '../../interfaces/UserDTO';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl: string = 'http://localhost:8081/users';

  constructor(private http: HttpClient) { }

  /**
   * Service to get user by username and password
   * @param username
   * @param password
   * @returns
   */
  public getUserByUsernamePassword(username: string, password: string): Observable<UserDTO> {
    return this.http.post<UserDTO>(`${this.apiUrl}/login`, { username, password });
  }

}
