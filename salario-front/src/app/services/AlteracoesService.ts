// src/app/services/alteracoes.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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

@Injectable({
  providedIn: 'root'
})
export class AlteracoesService {
  private apiUrl = 'http://localhost:8080/api/alteracoes';

  constructor(private http: HttpClient) {}

  listarTodas(): Observable<AlteracaoFolhaDTO[]> {
    return this.http.get<AlteracaoFolhaDTO[]>(this.apiUrl);
  }
}
