import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../enviroment';


@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = environment.apiBaseUrl + '/auth';

  constructor(private http: HttpClient) {}

  login(credentials: { username: string; password: string }) {
    return this.http.post(`${this.apiUrl}/login`, credentials);
  }

  getUserDetails() {
    return this.http.get(`${this.apiUrl}/user`);
  }

  getRole(): string {
 
      return (localStorage.getItem('role') || '').toUpperCase().trim();
  
}

 

  getUsername(): string {
    return localStorage.getItem('username') || '';
  }

  
  logout(): void {
    localStorage.removeItem('token'); // Remove the token from localStorage
  }




}

/*import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  // Método para autenticar o usuário
  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { username, password });
  }

  // Método para obter o papel do usuário
  getRole(): string {
    const token = localStorage.getItem('token');
    if (!token) {
      return '';
    }
    // Decodificar o token JWT para obter o papel (exemplo simplificado)
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload.role || '';
  }

  // Método para logout
  logout(): void {
    localStorage.removeItem('token');
  }

  // Método para obter dados do dashboard
  getDashboard(): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(`${this.apiUrl}/dashboard`, { headers });


}  } */