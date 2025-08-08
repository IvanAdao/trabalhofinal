import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../enviroment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  loading = false;
  error: string | null = null;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    this.error = null;
    if (this.loginForm.invalid) return;

    this.loading = true;

    this.http.post(`${environment.apiBaseUrl}/auth/login`, this.loginForm.value).subscribe({
      next: (res: any) => {
        this.loading = false;
        localStorage.setItem('token', res.token);
        localStorage.setItem('username', res.username);
        localStorage.setItem('role', res.role);
        window.location.href = '/dashboard';
      },
      error: err => {
        this.loading = false;
        console.error('Erro no login:', err);
        this.error = err.error?.message || 'Falha ao tentar conectar ao servidor';
      }
    });
  }
}
