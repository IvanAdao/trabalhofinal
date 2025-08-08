import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-investigacao-form',
  templateUrl: './investigacao-form.component.html',
  styleUrls: ['./investigacao-form.component.scss']
})
export class InvestigacaoFormComponent implements OnInit {
  nomeServidor!: string;
  camposModificados!: string;
  idServidor!: number;
  funcionarioResponsavel!: string;
  comentarios!: string;
  estado: string = 'EM_ANALISE';
  mostrarFormulario = false;
  idAlteracao!: number; // Certifique-se de preencher este campo ao abrir o formulário

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idAlteracao = +params['id'];
      // Buscar dados da alteração para preencher nomeServidor/camposModificados
      this.http.get<any>(`http://localhost:8080/api/alteracoes/${this.idAlteracao}`)
        .subscribe({
          next: (alt) => {
            this.nomeServidor = alt.nomeServidor;
            this.camposModificados = alt.campoModificado;
            this.idServidor = alt.idServidor; // Preencha aqui
            this.mostrarFormulario = false; // Só mostra após clicar
          },
          error: () => {
            alert('Erro ao buscar dados da alteração');
          }
        });
    });
  }

  iniciarInvestigacao() {
    const investigacao = {
      idAlteracao: this.idAlteracao,
      idServidor: this.idServidor,
      funcionarioResponsavel: this.funcionarioResponsavel,
      comentarios: this.comentarios,
      estado: this.estado
    };

    this.http.post('http://localhost:8080/api/investigacao', investigacao)
      .subscribe({
        next: () => {
          alert('Investigação criada!');
          // Redireciona para o dashboard, que recarrega os summary cards e a timeline
          this.router.navigate(['/dashboard/dashboard-alertas']);
        },
        error: () => alert('Erro ao criar investigação!')
      });
  }

  resolverSituacao() {
    const investigacao = {
      idAlteracao: this.idAlteracao,
      idServidor: this.idServidor,
      funcionarioResponsavel: this.funcionarioResponsavel,
      comentarios: this.comentarios,
      estado: this.estado
    };

    this.http.post('http://localhost:8080/api/investigacao', investigacao)
      .subscribe({
        next: () => {
          alert('Situação resolvida e investigação criada!');
          this.router.navigate(['/dashboard/dashboard-alertas']);
        },
        error: () => alert('Erro ao resolver situação!')
      });
  }
}
