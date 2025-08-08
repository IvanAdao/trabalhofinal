import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { jsPDF } from 'jspdf';

@Component({
  selector: 'app-exportar-casos-pdf',
  templateUrl: './exportar-casos-pdf.component.html',
  styleUrls: ['./exportar-casos-pdf.component.scss']
})
export class ExportarCasosPdfComponent implements OnInit {
  casos: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<any[]>('http://localhost:8080/api/investigacao/resolvidos')
      .subscribe(data => {
        this.casos = data.map(caso => ({
          ...caso,
          nome: caso.titulo, // ou caso.nome, conforme seu modelo
          selecionado: false
        }));
      });
  }

  exportarPDF() {
    const selecionados = this.casos.filter(c => c.selecionado);
    if (selecionados.length === 0) return;

    const doc = new jsPDF();
    doc.text('Casos Resolvidos Selecionados', 10, 10);
    selecionados.forEach((caso, i) => {
      doc.text(`${i + 1}. ${caso.nome}`, 10, 20 + i * 10);
    });
    doc.save('casos-resolvidos.pdf');
  }
}
