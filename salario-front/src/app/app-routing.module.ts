import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { CadastroServidorComponent } from './cadastro-servidor/cadastro-servidor.component';
import { TimelineComponent } from './timeline/timeline.component';
import { DashboardAlertasComponent } from './dashboard-alertas/dashboard-alertas.component';
import { InvestigacaoFormComponent } from './investigacao-form/investigacao-form.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
    children: [
      { path: 'registar-usuario', component: UserRegistrationComponent },
      { path: 'cadastro-servidor', component: CadastroServidorComponent },
      { path: 'linha-tempo-logs', component: TimelineComponent },
      { path: 'dashboard-alertas', component: DashboardAlertasComponent },
      { path: 'investigacao-form', component: InvestigacaoFormComponent }
    ]
  },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule] // ✅ ESSENCIAL
})
export class AppRoutingModule { }
