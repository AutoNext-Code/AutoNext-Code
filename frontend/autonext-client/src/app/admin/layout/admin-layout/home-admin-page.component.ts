import { Component } from '@angular/core';
import { HeaderComponent } from '../../../shared/header/header.component';
import { SideBarComponent } from '../../components/side-bar/side-bar.component';
import { RouterModule } from '@angular/router';

@Component({
  imports: [HeaderComponent, SideBarComponent, RouterModule],
  templateUrl: './home-admin-page.component.html',
  styleUrl: './home-admin-page.component.css'
})
export class HomeAdminPageComponent {

}
