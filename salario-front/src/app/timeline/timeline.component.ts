import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlteracoesService, AlteracaoFolhaDTO } from '../services/AlteracoesService';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.scss'],
  standalone: true,
  imports: [CommonModule]
})
export class TimelineComponent implements OnInit {
  alteracoes: AlteracaoFolhaDTO[] = [];

  constructor(
    private http: HttpClient,
    private alteracoesService: AlteracoesService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const tipo = params['tipo'];
      if (tipo === 'suspeita') {
        // Busca apenas suspeitas (alertaActivado = true)
        this.http.get<AlteracaoFolhaDTO[]>('http://localhost:8080/api/alteracoes/alertas')
          .subscribe(alts => this.alteracoes = alts);
      } else {
        // Busca todas as alterações
        this.http.get<AlteracaoFolhaDTO[]>('http://localhost:8080/api/alteracoes')
          .subscribe(alts => this.alteracoes = alts);
      }
    });
  }

  abrirInvestigacao(alt: AlteracaoFolhaDTO) {
    this.router.navigate(['/dashboard/investigacao-form'], { queryParams: { id: alt.idAlteracao } });
  }
}
