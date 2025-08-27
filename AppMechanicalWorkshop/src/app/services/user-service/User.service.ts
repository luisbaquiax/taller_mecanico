import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDTO } from '../../interfaces/UserDTO';
import { RegisterClientDTO as RegisterClientDTO } from '../../interfaces/RegisterClient';

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

  /**
   * Service to register a new user
   * @param user
   * @returns
   */
  public registerUser(user: UserDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(`${this.apiUrl}/createUser`, user);
  }

  /**
   * Service to register a new client
   * @param client
   * @returns
   */
  public regiterClient(client: RegisterClientDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(`${this.apiUrl}/createClient`, client);
  }

  /**
   * List all users
   * @returns All users
   */
  public getAllUsers(): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.apiUrl}/all`);
  }

  /**
   * List users by their active status
   * @param isActive
   * @returns Users filtered by active status
   */
  public getUserByIsActive(isActive: boolean): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.apiUrl}/isActive/${isActive}`);
  }

}
