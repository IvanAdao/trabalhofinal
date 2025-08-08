import { Component, OnInit } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

interface User {
  id: number;
  username: string;
  password: string;
  enabled: boolean;
  role: string;
}

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.scss']
})
export class UserRegistrationComponent implements OnInit {

  userForm: FormGroup;
  users: User[] = [];
  roles = ['RH', 'SUPERVISOR','AUDITOR','SERVIDOR'];
  editingUser: User | null = null;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.userForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      role: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.http.get<User[]>('http://localhost:8080/api/auth').subscribe({
      next: users => {
        this.users = users;
      },
      error: err => {
        alert('Erro ao carregar usuários');
      }
    });
  }

  submit() {
    if (this.userForm.invalid) return;
    const payload = {
      username: this.userForm.value.username,
      password: this.userForm.value.password,
      role: this.userForm.value.role
    };
    this.http.post('http://localhost:8080/api/auth/register', payload).subscribe({
      next: () => {
        alert('Usuário cadastrado com sucesso!');
        this.userForm.reset();
        this.loadUsers(); // Atualiza a lista após cadastro
      },
      error: err => {
        alert(err.error || 'Erro ao cadastrar usuário');
      }
    });
  }

  edit(user: User) {
    this.editingUser = user;
    this.userForm.patchValue({
      username: user.username,
      password: '',
      role: user.role
    });
  }

  toggleBlock(user: User) {
    this.http.patch(`http://localhost:8080/api/auth/toggle-enabled/${user.id}`, {}).subscribe({
      next: () => {
        user.enabled = !user.enabled;
        alert(user.enabled ? 'Usuário desbloqueado!' : 'Usuário bloqueado!');
      },
      error: () => {
        alert('Erro ao atualizar status do usuário');
      }
    });
  }

  cancelEdit() {
    this.editingUser = null;
    this.userForm.reset();
  }

  saveUserRole(dto: any, user: User) {
    const roleOpt = this.roles.find(role => role === dto.role);
    if (!roleOpt) {
      return alert("Perfil não encontrado");
    }
    user.role = roleOpt;
    // Aqui você pode implementar a chamada para atualizar o usuário no backend, se desejar.
  }
}

