import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module'; // ✅ ESSENCIAL
import { AuthRoutingModule } from './auth/auth-routing.module';

import { AppComponent } from './app.component';
import { LoginComponent } from './auth/login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { UserRegistrationComponent } from './user-registration/user-registration.component';
import { CadastroServidorComponent } from './cadastro-servidor/cadastro-servidor.component';
import { TimelineComponent } from './timeline/timeline.component';

import { AuthInterceptor } from './AuthInterceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavComponent } from './nav/nav.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { DashboardAlertasComponent } from './dashboard-alertas/dashboard-alertas.component';
import { NgChartsModule } from 'ng2-charts';

import { InvestigacaoFormComponent } from './investigacao-form/investigacao-form.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    UserRegistrationComponent,
    CadastroServidorComponent,
    NavComponent,
    DashboardAlertasComponent,
    InvestigacaoFormComponent,
    
    
    
  ],
  imports: [
    BrowserModule,
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule, // ✅ ESTE DEVE CONTER RouterModule.forRoot()
    AuthRoutingModule, BrowserAnimationsModule, MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule, MatListModule,
    NgChartsModule // Importando o módulo de gráficos
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
