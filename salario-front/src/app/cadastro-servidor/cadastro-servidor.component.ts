// cadastro-servidor.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ServidorService } from '../services/servidor';

@Component({
  selector: 'app-cadastro-servidor',
  templateUrl: './cadastro-servidor.component.html',
  styleUrls: ['./cadastro-servidor.component.scss']
})
export class CadastroServidorComponent implements OnInit {
  servidorForm: FormGroup;
  sucesso = false;
  erro = '';
  editando = false;
  servidorSelecionadoId: number | null = null;
  servidores: any[] = [];

  constructor(private fb: FormBuilder, private servidorService: ServidorService) {
    this.servidorForm = this.fb.group({
      nomeCompleto: ['', Validators.required],
      nuit: ['', Validators.required],
      nif: ['', Validators.required],
      cargo: ['', Validators.required],
      orgao: ['', Validators.required],
      salarioBase: ['', [Validators.required, Validators.min(0)]],
      estado: ['Activo', Validators.required]
    });
  }

  ngOnInit() {
    this.servidorService.listarServidores().subscribe(data => {
      this.servidores = data;
    });
  }

  onSubmit() {
    this.erro = '';
    this.sucesso = false;

    if (this.servidorForm.invalid) return;

    if (this.editando && this.servidorSelecionadoId) {
      // Atualizar servidor existente
      this.servidorService.actualizarServidor(this.servidorSelecionadoId, this.servidorForm.value).subscribe({
        next: () => {
          this.sucesso = true;
          this.editando = false;
          this.servidorSelecionadoId = null;
          this.servidorForm.reset();
        },
        error: (err: any) => {
          if (typeof err.error === 'string') {
            this.erro = err.error;
          } else if (err.status === 0) {
            this.erro = 'Não foi possível conectar ao servidor.';
          } else {
            this.erro = 'Erro ao actualizar servidor';
          }
        }
      });
    } else {
      // Criar novo servidor
      this.servidorService.criarServidor(this.servidorForm.value).subscribe({
        next: () => {
          this.sucesso = true;
          this.servidorForm.reset();
        },
        error: (err: { error: string; }) => {
          this.erro = err.error || 'Erro ao salvar servidor';
        }
      });
    }
  }

  limparFormulario() {
    this.servidorForm.reset();
    this.editando = false;
    this.servidorSelecionadoId = null;
    this.sucesso = false;
    this.erro = '';
  }

  carregarServidorParaEdicao(servidor: any) {
    this.servidorForm.patchValue(servidor);
    this.editando = true;
    this.servidorSelecionadoId = servidor.idServidor;
    this.sucesso = false;
    this.erro = '';
  }

  removerServidor() {
    if (this.servidorSelecionadoId) {
      this.servidorService.apagarServidor(this.servidorSelecionadoId).subscribe({
        next: () => {
          this.sucesso = true;
          this.limparFormulario();
        },
        error: (err: { error: string; }) => {
          this.erro = err.error || 'Erro ao remover servidor';
        }
      });
    }
  }
}
