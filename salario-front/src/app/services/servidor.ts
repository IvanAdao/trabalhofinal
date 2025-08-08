// src/app/services/servidor.service.ts
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { environment } from '../enviroment';
export interface AlteracaoFolhaDTO {
  idAlteracao: number;
  nomeServidor: string;
  campoModificado: string;
  valorAnterior: string;
  valorNovo: string;
  motivo?: string;
  dataAlteracao: string;
  ipOrigem?: string;
  alertaActivado?: boolean;
}
@Injectable({ providedIn: 'root' })
export class ServidorService {
    private api = `${environment.apiBaseUrl}/servidores`;
  private apiUrl = `${environment.apiBaseUrl}/alteracoes`;
  //private api = 'http://localhost:8080/api/servidores';
  //private apiUrl = 'http://localhost:8080/api/alteracoes';

  
   constructor(private http: HttpClient, private authService: AuthService) {}

  actualizarServidor(id: number, servidor: any) {
    const usuarioId = this.authService.getRole();

    const headers = new HttpHeaders({
      'X-Usuario-Id': usuarioId?.toString() || '',
      'X-Ip-Origem': '127.0.0.1' // você pode automatizar isso depois também
    });
    if (!usuarioId) {
      throw new Error('Usuário não autenticado');
    }
    return this.http.put(`${this.api}/${id}`, servidor, { headers });
  }

  criarServidor(data: any): Observable<any> {
    return this.http.post(this.api, data);
  }

  listarServidores(): Observable<any[]> {
    return this.http.get<any[]>(this.api);
  }

  editarServidor(id: number, data: any): Observable<any> {
    return this.http.put(`${this.api}/${id}`, data);
  }

  apagarServidor(id: number): Observable<any> {
    return this.http.delete(`${this.api}/${id}`);
  }
  getAlteracoesByServidor(id: number): Observable<any[]> {
  return this.http.get<any[]>(`${this.api}/alteracoes${id}`);
}
listarTodas(): Observable<AlteracaoFolhaDTO[]> {
    return this.http.get<AlteracaoFolhaDTO[]>(this.apiUrl);
  }

  listarPorServidor(idServidor: number): Observable<AlteracaoFolhaDTO[]> {
    return this.http.get<AlteracaoFolhaDTO[]>(`${this.apiUrl}/servidor/${idServidor}`);
  }
  
}
