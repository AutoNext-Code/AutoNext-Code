import { Component } from '@angular/core';
import { HeaderComponent } from '@shared/header/header.component';
import { DashboardLayoutComponent } from "../../components/dashboard-layout/dashboard-layout.component";

@Component({
  selector: 'app-user-dashboard',
  imports: [HeaderComponent, DashboardLayoutComponent],
  templateUrl: './user-dashboard.component.html',
  styleUrl: './user-dashboard.component.css'
})
export class UserDashboardComponent {

}
