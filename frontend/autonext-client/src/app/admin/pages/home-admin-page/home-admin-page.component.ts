import { Component } from '@angular/core';
import { HeaderComponent } from '../../../shared/header/header.component';
import { SideBarComponent } from '../../components/side-bar/side-bar.component';

@Component({
  imports: [HeaderComponent, SideBarComponent],
  templateUrl: './home-admin-page.component.html',
  styleUrl: './home-admin-page.component.css'
})
export class HomeAdminPageComponent {

}
