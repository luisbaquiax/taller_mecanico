import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDTO } from '../../interfaces/UserDTO';
import { RegisterClientDTO as RegisterClientDTO } from '../../interfaces/RegisterClient';
import { UserRegisterDTO } from '../../interfaces/UserRegisterDTO';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiUrl: string = 'http://localhost:8081/users';

  constructor(private http: HttpClient) {}

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
  public registerUser(user: UserRegisterDTO, typeSupplier: string): Observable<UserDTO> {
    return this.http.post<UserDTO>(`${this.apiUrl}/createUser/${typeSupplier}`, user);
  }

  /**
   * Service to update a user
   * @param user
   * @returns
   */
  public updateUser(user: UserDTO): Observable<UserDTO> {
    return this.http.put<UserDTO>(`${this.apiUrl}/updateUser`, user);
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

  /**
   * Get user by ID
   * @param userId
   * @returns UserDTO
   */
  public getUserById(userId: number): Observable<UserDTO> {
    return this.http.get<UserDTO>(`${this.apiUrl}/${userId}`);
  }

  /**
   * Change user password
   * @param passwordData
   * @returns Response
   */
  public changePassword(passwordData: {
    userId: number;
    currentPassword: string;
    newPassword: string;
  }): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/changePassword`, passwordData);
  }

  /**
   * Test 2FA code sending
   * @param testData
   * @returns Response
   */
  public test2FA(testData: {
    userId: number;
    email: string;
    phone: string;
    type: number;
  }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/test2fa`, testData);
  }

  /**
   * Send verification code for 2FA
   * @param userData
   * @returns Response
   */
  public send2FACode(userData: {
    userId: number;
    email?: string;
    phone?: string;
    type: number;
  }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/send2fa`, userData);
  }

  /**
   * Verify 2FA code
   * @param verifyData
   * @returns Response
   */
  public verify2FACode(verifyData: {
    userId: number;
    code: string;
    type: number;
  }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/verify2fa`, verifyData);
  }

  /**
   * Enable/Disable 2FA for user
   * @param userId
   * @param enabled
   * @param type
   * @returns Response
   */
  public toggle2FA(userId: number, enabled: boolean, type?: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/toggle2fa`, {
      userId,
      enabled,
      type: type || 2 // Email por defecto
    });
  }
}
