import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  activeMenu: string = '';
  username: string = '';
  role: string = '';

  constructor(private authService: AuthService, private router: Router) {}
ngOnInit() {
  this.username = this.authService.getUsername();
  this.role = this.authService.getRole()?.toUpperCase().trim(); // força letras maiúsculas


}



  navigateTo(menu: string) {
    this.activeMenu = menu;
    this.router.navigate(['dashboard', menu]);
    
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
  
}