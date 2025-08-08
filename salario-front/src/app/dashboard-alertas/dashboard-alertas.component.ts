import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

interface AlteracaoFolhaDTO {
  idAlteracao: number;
  nomeServidor: string;
  campoModificado: string;
  valorAnterior: string;
  valorNovo: string;
  motivo: string;
  dataAlteracao: string;
  ipOrigem: string;
  alertaActivado: boolean;
}

@Component({
  selector: 'app-dashboard-alertas',
  templateUrl: './dashboard-alertas.component.html',
  styleUrls: ['./dashboard-alertas.component.scss']
})
export class DashboardAlertasComponent implements OnInit {
  totalAlertas = 0;
  totalSuspeitas = 0;
  totalSemRisco = 0;
  totalAlteracoes = 0;
  chartData: any;
  alertasSuspeitos: AlteracaoFolhaDTO[] = [];
  
  // Chart data and options for Monitoramento de Riscos (existing chart)
  chartOptions: any = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'bottom'
      }
    }
  };
  
  // Chart data and options for Tendência de Fraudes
  fraudTrendChartData: any;
  fraudTrendChartOptions: any = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'bottom'
      }
    },
    scales: {
      y: {
        beginAtZero: true
      }
    }
  };
  
  // Chart data and options for Distribuição de Alterações
  alteracoesDistributionChartData: any;
  alteracoesDistributionChartOptions: any = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'bottom'
      }
    }
  };

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.carregarEstatisticas();
    this.carregarAlertasSuspeitos();
    
    // Subscribe to router events to detect when the component is activated again
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event) => {
      // Type guard to ensure we have a NavigationEnd event
      if (event instanceof NavigationEnd) {
        // Check if we're navigating to the dashboard-alertas route
        if (event.urlAfterRedirects === '/dashboard/dashboard-alertas') {
          this.carregarEstatisticas();
          this.carregarAlertasSuspeitos();
        }
      }
    });
  }

  carregarEstatisticas() {
    this.http.get<any>('http://localhost:8080/api/alteracoes/dashboard-estatisticas')
      .subscribe(res => {
        this.totalAlertas = res.fraude;
        this.totalSuspeitas = res.suspeita;
        this.totalSemRisco = res.semRisco;
        this.totalAlteracoes = res.total;
        this.chartData = {
          labels: ['Fraude', 'Alterações suspeitas', 'Sem risco'],
          datasets: [{
            data: [res.fraude, res.suspeita, res.semRisco],
            backgroundColor: ['#ef476f', '#ffd166', '#06d6a0'],
            hoverOffset: 12,
            borderWidth: 2,
            borderColor: '#ffffff'
          }]
        };
        
        // Placeholder data for Tendência de Fraudes chart
        const last7Days = [];
        const fraudData = [];
        for (let i = 6; i >= 0; i--) {
          const date = new Date();
          date.setDate(date.getDate() - i);
          last7Days.push(date.toLocaleDateString('pt-BR', { weekday: 'short' }));
          // Generate random fraud data for demonstration
          fraudData.push(Math.floor(Math.random() * 10));
        }
        
        this.fraudTrendChartData = {
          labels: last7Days,
          datasets: [{
            label: 'Fraudes',
            data: fraudData,
            backgroundColor: '#ef476f',
            borderColor: '#d32f2f',
            borderWidth: 1
          }]
        };
        
        // Placeholder data for Distribuição de Alterações chart
        this.alteracoesDistributionChartData = {
          labels: ['Salário', 'Cargo', 'Nuit', 'Nif', 'Estado'],
          datasets: [{
            data: [45, 25, 15, 10, 5],
            backgroundColor: ['#1976d2', '#ef476f', '#ffd166', '#06d6a0', '#6a1b9a'],
            hoverOffset: 12,
            borderWidth: 2,
            borderColor: '#ffffff'
          }]
        };
      });
  }

  carregarAlertasSuspeitos() {
    this.http.get<AlteracaoFolhaDTO[]>('http://localhost:8080/api/alteracoes/alertas')
      .subscribe(alertas => this.alertasSuspeitos = alertas);
  }

  atualizarTudo() {
    this.carregarEstatisticas();
    this.carregarAlertasSuspeitos();
    this.atualizarLinhaTempo();
  }

  atualizarLinhaTempo() {
    this.router.navigate(['/dashboard/linha-tempo-logs'], { queryParams: { tipo: 'suspeita', reload: new Date().getTime() } });
  }

  verSuspeitasNaLinhaTempo() {
    this.router.navigate(['/dashboard/linha-tempo-logs'], { queryParams: { tipo: 'suspeita' } });
  }

  resolverAlteracao(idAlteracao: number) {
    this.http.put(`http://localhost:8080/api/alteracoes/${idAlteracao}/resolver`, {})
      .subscribe({
        next: () => {
          this.atualizarTudo();
          alert('Alteração marcada como resolvida!');
        },
        error: () => alert('Erro ao resolver alteração')
      });
  }
  
}
