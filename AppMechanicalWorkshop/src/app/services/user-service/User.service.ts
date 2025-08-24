import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl: string = 'http://localhost:8081/users';

  constructor(private http: HttpClient) { }


  public getUserByUsernamePassword(username: string, password: string) {
    return this.http.post(`${this.apiUrl}/login`, { username, password });
  }

}
