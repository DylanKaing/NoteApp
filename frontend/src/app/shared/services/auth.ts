import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class Auth {
  
  private apiUrl = 'http://localhost:8080/api/users';

  // Inject HttpClient to make HTTP calls
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<string> {
    return this.http.post(this.apiUrl + '/login', { username, password}, { responseType: 'text'});
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(this.apiUrl + '/register', { username, email, password });
  }

  storeToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('token') !== null;
  }
}
